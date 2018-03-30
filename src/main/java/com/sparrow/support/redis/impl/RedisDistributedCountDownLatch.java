/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sparrow.support.redis.impl;

import com.sparrow.cache.CacheClient;
import com.sparrow.constant.cache.KEY;
import com.sparrow.exception.CacheConnectionException;
import com.sparrow.support.latch.DistributedCountDownLatch;
import com.sparrow.utility.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author harry
 */
public class RedisDistributedCountDownLatch implements DistributedCountDownLatch {
    private static Logger logger = LoggerFactory.getLogger(RedisDistributedCountDownLatch.class);
    private CacheClient cacheClient;

    public RedisDistributedCountDownLatch() {
    }

    public RedisDistributedCountDownLatch(CacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    public void setCacheClient(CacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }




    @Override
    public void consume(KEY consumerKey, final String key) {
        if (consumerKey == null) {
            throw new UnsupportedOperationException("product key is null");
        }
        while (true) {
            try {
                cacheClient.set().add(consumerKey, key);
                return;
            } catch (CacheConnectionException e) {
                logger.error("monitor consume connection break ", e);
            }
        }
    }

    @Override
    public boolean consumable(KEY consumeKey,String keys) {
        try {
            return !cacheClient.set().exist(consumeKey,keys);
        } catch (CacheConnectionException e) {
            return true;
        }
    }

    @Override
    public void product(KEY productKey, final String key) {
        if (productKey == null) {
            throw new UnsupportedOperationException("product key is null");
        }
        while (true) {
            try {
                cacheClient.set().add(productKey, key);
                return;
            } catch (CacheConnectionException e) {
                logger.error("productKey product connection break ", e);
            }
        }
    }

    @Override
    public boolean isFinish(KEY productKey,KEY consumerKey) {
        if (StringUtility.isNullOrEmpty(productKey)) {
            throw new UnsupportedOperationException("product key is null");
        }
        try {
            Long productCount = cacheClient.set().getSize(productKey);
            Long consumerCount=cacheClient.set().getSize(consumerKey);
            Boolean match = productCount.equals(consumerCount);
            logger.info("product key {}:product count {}-->consumer count {},match {}", productKey.key(),
                productCount,consumerCount,
                match);
            if (!match) {
                return false;
            }
        } catch (CacheConnectionException e) {
            return false;
        }
        while (true) {
            try {
                cacheClient.key().delete(productKey);
                cacheClient.key().delete(consumerKey);
                return true;
            } catch (CacheConnectionException ignore) {
                logger.error("monitor error", ignore);
            }
        }
    }

    @Override
    public boolean monitor(KEY productKey,KEY consumerKey, int secondInterval) {
        while (true) {
            if (isFinish(productKey,consumerKey)) {
                return true;
            }
            try {
                Thread.sleep(1000 * secondInterval);
            } catch (InterruptedException ignore) {
            }
        }
    }

    @Override
    public boolean monitor(KEY productKey,KEY consumerKey) {
        return this.monitor(productKey,consumerKey, 2);
    }
}
