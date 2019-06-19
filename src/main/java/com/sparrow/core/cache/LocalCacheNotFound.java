package com.sparrow.core.cache;

public interface LocalCacheNotFound <T> {
    T read(String key);
}