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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.sparrow.constant.CACHE_KEY;

/**
 * 系统缓存类 <p> 加泛型为上层调用时不用类型转换
 *
 * @author harry
 * @version 1.0
 */
public class Cache {
    private Map<String, Map<String, ?>> map = new ConcurrentHashMap<String, Map<String, ?>>();

    private static class Nested{
        private static Cache cache=new Cache();
    }


    public static Cache getInstance() {
        return Nested.cache;
    }

    /**
     * 获取map中的值
     *
     * @param key key
     * @return value
     */
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

    /**
     * 从二级缓存中获取值 不过期
     *
     * @param key key
     * @return value
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String childCacheKey, String key) {
        Map<String, ?> childCache = this.get(this.map, childCacheKey);
        if (childCache == null) {
            return null;
        }
        return (T) this.get(childCache, key);
    }

    /**
     * 从二级默认缓存中获取
     *
     * @param key key
     * @return value
     */
    public <T> T getValueFromDefaultCache(String key) {
        return this.get(CACHE_KEY.DEFAULT, key);
    }

    /**
     * 存入一级缓存 <p> 只接收Map <p> 业务上只接受不变的数据
     *
     * @param key key
     * @param value value
     */
    public <T> void put(String key, Map<String, T> value) {
        this.map.put(key, value);
    }

    /**
     * 存入二级缓存 不过期直接保存缓存值
     *
     * @param childCacheKey childCacheKey
     * @param key key
     * @param value value
     */
    public <T> void put(String childCacheKey, String key, T value) {
        @SuppressWarnings("unchecked")
        Map<String, T> childCache = (Map<String, T>) this.get(this.map,
            childCacheKey);
        if (childCache == null) {
            childCache = new ConcurrentHashMap<String, T>(1024);
            this.map.put(childCacheKey, childCache);
        }
        childCache.put(key, value);
    }

    /**
     * 存入二级默认缓存 不过期直接保存缓存值
     *
     * @param key key
     * @param value value
     */
    public <T> void putToDefaultCache(String key, T value) {
        this.put(CACHE_KEY.DEFAULT, key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> Map<String, T> get(String key) {
        return (Map<String, T>) Nested.cache.get(this.map, key);
    }

    public void clear() {
        map.clear();
    }
}
