package com.sparrow.container;

public interface FactoryBean<T> {
    void pubObject(String name, T o);

    T getObject(String name);

    Class<?> getObjectType();

    void removeObject(String name);
}