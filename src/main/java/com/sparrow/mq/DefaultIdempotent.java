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
package com.sparrow.mq;

import com.sparrow.cache.CacheClient;
import com.sparrow.constant.cache.KEY;
import com.sparrow.constant.cache.key.KeyMQIdempotent;
import com.sparrow.protocol.constant.magic.DIGIT;
import com.sparrow.exception.CacheConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author by harry
 */
public class DefaultIdempotent implements MQIdempotent {
    private static Logger logger = LoggerFactory.getLogger(DefaultIdempotent.class);
    private CacheClient cacheClient;

    public void setCacheClient(CacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    @Override
    public boolean duplicate(String keys) {
        while (true) {
            try {
                KEY key = new KEY.Builder().business(KeyMQIdempotent.IDEMPOTENT).businessId(keys).build();
                Integer value = cacheClient.string().get(key, Integer.class);
                return value != null && value.equals(DIGIT.ONE);
            } catch (CacheConnectionException e) {
                logger.error("consumable connection break ", e);
            }
        }
    }

    @Override public boolean consumed(String keys) {
        while (true) {
            try {
                KEY consumeKey = new KEY.Builder().business(KeyMQIdempotent.IDEMPOTENT).businessId(keys).build();
                //redlock setExpire(key,timestamp)
                Long value = cacheClient.string().setIfNotExist(consumeKey, DIGIT.ONE);
                if (value > 0) {
                    cacheClient.key().expire(consumeKey, 60 * 60 * 72);
                    return true;
                }
                return false;
            } catch (CacheConnectionException e) {
                logger.error("consumable connection break ", e);
            }
        }
    }
}
