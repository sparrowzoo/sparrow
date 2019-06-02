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

package com.sparrow.core;

import com.sparrow.concurrent.SparrowThreadFactory;
import com.sparrow.core.algorithm.bus.BatchEventBus;
import com.sparrow.utility.CollectionsUtility;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 系统缓存类 <p> 加泛型为上层调用时不用类型转换
 *
 * @author harry
 * @version 1.0
 */
public class Cache {
    private Map<String, SoftReference<ExpirableData<Map<String, ?>>>> expirableMap = new ConcurrentHashMap<>();

    private Map<String, Map<String, ?>> map = new ConcurrentHashMap<>();

    static {
        ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor(new SparrowThreadFactory.Builder().namingPattern("cache-expire-cleaner").build());
        cleaner.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Map<String, SoftReference<ExpirableData<Map<String, ?>>>> expirableDataMap = Cache.getInstance().expirableMap;
                if (expirableDataMap == null || expirableDataMap.size() == 0) {
                    return;
                }

                for (String key : expirableDataMap.keySet()) {
                    SoftReference<ExpirableData<Map<String, ?>>> expirableData = expirableDataMap.get(key);
                    if(expirableData==null){
                        continue;
                    }
                    ExpirableData<Map<String,?>> data= expirableData.get();
                    if(data==null){
                        continue;
                    }
                    if (data.isExpire()) {
                        expirableDataMap.remove(key);
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private static class Nested {
        private static Cache cache = new Cache();
    }

    public static Cache getInstance() {
        return Nested.cache;
    }

    private <T> T get(Map<String, T> map, String key) {
        if (!map.containsKey(key)) {
            return null;
        }
        T value = map.get(key);
        if (value == null) {
            map.remove(key);
            return null;
        }
        return value;
    }

    public <T> T get(String L1Key, String L2Key) {
        Map<String, ?> childCache = this.get(this.map, L1Key);
        if (childCache == null) {
            return null;
        }
        return (T) this.get(childCache, L2Key);
    }


    public <T> void put(String key, Map<String, T> value) {
        this.map.put(key, value);
    }


    public <T> void put(String key, Map<String, T> value, int expire) {
        this.expirableMap.put(key, new SoftReference<>(new ExpirableData(expire, value)));
    }

    public <T> void put(String L1Key, String L2Key, T value) {
        @SuppressWarnings("unchecked")
        Map<String, T> childCache = (Map<String, T>) this.get(this.map,
                L1Key);
        if (childCache == null) {
            childCache = new ConcurrentHashMap<String, T>(1024);
            this.map.put(L1Key, childCache);
        }
        childCache.put(L2Key, value);
    }


    @SuppressWarnings("unchecked")
    public <T> Map<String, T> get(String key) {
        return (Map<String, T>) Nested.cache.get(this.map, key);
    }

    public ExpirableData<Map<String, ?>> getExpirable(String key) {
        SoftReference<ExpirableData<Map<String,?>>> softReference= Nested.cache.get(this.expirableMap, key);
        if(softReference==null){
            return null;
        }
        return softReference.get();
    }

    public void clear() {
        map.clear();
    }
}
