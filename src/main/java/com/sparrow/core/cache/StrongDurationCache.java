package com.sparrow.core.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: zh_harry@163.com
 * @date: 2019/6/25 14:14
 * @description:
 */
public class StrongDurationCache<K, V> implements Cache<K, V> {

    private Map<K, V> cache = new ConcurrentHashMap<>();

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public V getIfPresent(K key) {
        return cache.get(key);
    }

    @Override
    public Map<K, V> getAllPresent(Iterable<K> keys) {
        Map<K, V> map = new HashMap<>();
        for (K k : keys) {
            map.put(k, cache.get(k));
        }
        return map;
    }

    @Override
    public long size() {
        return cache.size();
    }

    @Override
    public ConcurrentMap<K, V> asMap() {
        return new ConcurrentHashMap<>(cache);
    }

    @Override
    public void invalidate(Object key) {
        throw new UnsupportedOperationException("invalidate date not support");
    }

    @Override
    public void invalidateAll(Iterable<K> keys) {
        throw new UnsupportedOperationException("invalidate date not support");
    }

    @Override
    public void invalidateAll() {
        throw new UnsupportedOperationException("invalidate date not support");
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
