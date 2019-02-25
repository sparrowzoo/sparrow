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
package com.sparrow.core.algorithm.bucket;

import com.sparrow.concurrent.SparrowThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author by harry
 */
public class Bucket<T> {
    private List<T> bucket;
    private Integer size;

    private Integer batchCount = 0;

    private Overflow<T> overflow;

    private Long lastPersistTime=0L;

    public Bucket() {
        this(128);
    }

    public Bucket(int size){
        this(size,1);
    }

    public Bucket(Integer size,int periodSecond) {
        this(size,periodSecond, null);
    }

    public Bucket(Integer size,int periodSecond,Overflow<T> overflow) {
        this.bucket = new ArrayList<T>(size);
        this.size = size;
        this.overflow = overflow;

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new SparrowThreadFactory.Builder().namingPattern("bucket-persist-schedule-%d").build());
        executorService.scheduleAtFixedRate(new Runnable() {
            //超示2秒未满足size 则自动入库
            @Override
            public void run() {
                if (System.currentTimeMillis() - Bucket.this.lastPersistTime > 2000) {
                    Bucket.this.over();
                }
            }
        }, 0, periodSecond, TimeUnit.SECONDS);
    }


    public List<T> fill(T item) {
        bucket.add(item);
        if (bucket.size() >= this.size) {
            synchronized (this) {
                if(bucket.size()>=this.size) {
                    List<T> returnList = new ArrayList<T>(this.bucket);
                    this.bucket.clear();
                    this.batchCount++;
                    if (overflow != null) {
                        this.lastPersistTime = System.currentTimeMillis();
                        this.overflow.hook(returnList);
                    }
                    return returnList;
                }
            }
        }
        return null;
    }

    public List<T> getBucket() {
        return bucket;
    }

    public synchronized void clear() {
        this.bucket.clear();
        this.batchCount = 0;
    }

    public synchronized void over() {
        if (!this.isEmpty() && this.overflow != null) {
            this.lastPersistTime=System.currentTimeMillis();
            List<T> returnList = new ArrayList<T>(this.bucket);
            this.overflow.hook(returnList);
            this.clear();
        }
    }

    public boolean isEmpty() {
        return this.bucket.size() == 0;
    }

    public Integer getBatchCount() {
        return batchCount + (this.bucket.size() > 0 ? 1 : 0);
    }
}
