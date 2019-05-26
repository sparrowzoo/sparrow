package com.sparrow.orm;

import com.sparrow.constant.CACHE_KEY;
import com.sparrow.container.ClassFactoryBean;
import com.sparrow.core.Cache;
import com.sparrow.utility.StringUtility;
import java.util.Iterator;
import java.util.Map;

public class EntityManagerFactoryBean implements ClassFactoryBean<EntityManager> {
    private static class Nested {
        private static EntityManagerFactoryBean single = new EntityManagerFactoryBean();
    }

    public static EntityManagerFactoryBean getInstance() {
        return Nested.single;
    }

    @Override
    public void pubObject(String name, EntityManager o) {
        Cache.getInstance().put(CACHE_KEY.ORM,
                name, o);
    }

    @Override
    public EntityManager getObject(String name) {
        return Cache.getInstance().get(CACHE_KEY.ORM,
                name);
    }

    @Override
    public Class<?> getObjectType() {
        return EntityManager.class;
    }

    @Override
    public void removeObject(String name) {
        Cache.getInstance().get(CACHE_KEY.ORM).remove(name);
    }

    @Override public Iterator<String> keyIterator() {
        Map<String,Object> map= Cache.getInstance().get(CACHE_KEY.ORM);
        if(map==null){
            return null;
        }
        return map.keySet().iterator();
    }

    @Override
    public void pubObject(Class clazz, EntityManager o) {
        Cache.getInstance().put(CACHE_KEY.ORM,
                StringUtility.getEntityNameByClass(clazz), o);
    }

    @Override
    public EntityManager getObject(Class clazz) {
        return Cache.getInstance().get(CACHE_KEY.ORM,
                StringUtility.getEntityNameByClass(clazz));
    }
}
