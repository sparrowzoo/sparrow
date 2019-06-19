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

package com.sparrow.core.cache;

import com.sparrow.concurrent.SparrowThreadFactory;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author harry
 * @version 1.0
 */
public class Cache {
    private Runnable innerSyncTask = new Runnable() {
        @Override
        public void run() {
            Cache cache = Cache.this;
            if (syncTask == null) {
                return;
            }
            Map<String, Object> newCache = syncTask.sync();
            for (String key : newCache.keySet()) {
                Object value = newCache.get(key);
                ExpirableData<Object> expirableData = cache.getExpirable(key);
                if (expirableData == null) {
                    cache.put(key, value, syncTask.getExpire(key));
                    continue;
                }
                cache.put(key,value, expirableData.getSeconds());
            }
        }
    };


    private Cache() {
        ScheduledExecutorService sync = Executors.newSingleThreadScheduledExecutor(new SparrowThreadFactory.Builder().namingPattern("cache-sync").build());
        sync.scheduleAtFixedRate(innerSyncTask, 0, 5, TimeUnit.SECONDS);
    }

    public void sync() {
        if (syncTask == null) {
            throw new NullPointerException("please init sync task");
        }
        innerSyncTask.run();
    }

    private Map<String, SoftReference<ExpirableData<Object>>> expirableMap = new ConcurrentHashMap<>();

    private Map<String, Object> map = new ConcurrentHashMap<>();

    private SyncTask syncTask;

    public void setSyncTask(SyncTask syncTask) {
        this.syncTask = syncTask;
    }

    static {
        ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor(new SparrowThreadFactory.Builder().namingPattern("cache-expire-cleaner").build());
        cleaner.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Map<String, SoftReference<ExpirableData<Object>>> expirableDataMap = Cache.getInstance().expirableMap;
                if (expirableDataMap == null || expirableDataMap.size() == 0) {
                    return;
                }

                for (String key : expirableDataMap.keySet()) {
                    SoftReference<ExpirableData<Object>> expirableData = expirableDataMap.get(key);
                    if (expirableData == null) {
                        continue;
                    }
                    ExpirableData<Object> data = expirableData.get();
                    if (data == null) {
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

    public <T> void put(String key, T value) {
        this.map.put(key, value);
    }

    public <T> void put(String L1Key, String L2Key, T value) {
        @SuppressWarnings("unchecked")
        Map<String, T> childCache = (Map<String, T>) this.map.get(L1Key);
        if (childCache == null) {
            childCache = new ConcurrentHashMap<String, T>(1024);
            this.map.put(L1Key, childCache);
        }
        childCache.put(L2Key, value);
    }


    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) this.map.get(key);
    }

    public <T> T get(String L1Key, String L2Key) {
        Map<String, ?> childCache = (Map<String, ?>) this.map.get(L1Key);
        if (childCache == null) {
            return null;
        }
        return (T) childCache.get(L2Key);
    }

    public ExpirableData<Object> getExpirable(String key) {
        return getExpirable(key, null, 0);
    }

    public <T> ExpirableData<Object> getExpirable(String key, LocalCacheNotFound<Object> hook, int expire) {
        SoftReference<ExpirableData<Object>> softReference = this.expirableMap.get(key);
        if (softReference != null) {
            return softReference.get();
        }
        if (hook == null) {
            return null;
        }
        Object o = hook.read(key);
        //if null cache shorten expire time
        expire = (o == null) ? (int) Math.ceil(expire / 10D) : expire;
        ExpirableData e = new ExpirableData<>(expire, o);
        this.expirableMap.put(key, new SoftReference<>(e));
        return e;
    }

    public <T> void put(String key, T value, int expire) {
        this.expirableMap.put(key, new SoftReference<>(new ExpirableData(expire, value)));
    }

    public void clear() {
        map.clear();
    }
}
