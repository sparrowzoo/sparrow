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

package com.sparrow.utility;

import com.sparrow.protocol.constant.magic.SYMBOL;

import com.sparrow.protocol.constant.CONSTANT;
import com.sparrow.protocol.db.MethodOrder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author harry
 */
public class ClassUtility {

    public static String getEntityNameByClass(Class entity) {
        String entityName = entity.getSimpleName();
        entityName = StringUtility.setFirstByteLowerCase(entityName);
        if (entityName.endsWith("DTO")) {
            return entityName.substring(0, entityName.lastIndexOf("DTO"));
        }
        if (entityName.endsWith("VO")) {
            return entityName.substring(0, entityName.lastIndexOf("VO"));
        }

        if (entityName.endsWith("BO")) {
            return entityName.substring(0, entityName.lastIndexOf("BO"));
        }
        return entityName;
    }

    /**
     * @param c 接口
     * @return 实现接口的所有类
     */
    public static List<Class> getAllClassByInterface(Class c) {
        List<Class> clazzList = new ArrayList<Class>();
        if (!c.isInterface()) {
            return clazzList;
        }
        String packageName = c.getPackage().getName();
        try {
            List<Class> allClass = getClasses(packageName);
            for (Class clazz : allClass) {
                if (!c.isAssignableFrom(clazz)) {
                    continue;
                }
                if (!c.equals(clazz)) {
                    clazzList.add(clazz);
                }
            }
        } catch (Exception ignore) {
        }
        return clazzList;
    }

    /**
     * @param packageName 包名
     * @return 包下所有类
     * @throws ClassNotFoundException, IOException, URISyntaxException
     */
    public static List<Class> getClasses(
            String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(SYMBOL.DOT, SYMBOL.SLASH);
        Enumeration<URL> resources = classLoader.getResources(path);
        ArrayList<Class> classes = new ArrayList<Class>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if ("file".equalsIgnoreCase(resource.getProtocol())) {
                File directory = new File(URLDecoder.decode(resource.getFile(), CONSTANT.CHARSET_UTF_8));
                classes.addAll(findClass(directory, packageName));
            } else if ("jar".equalsIgnoreCase(resource.getProtocol())) {
                classes.addAll(findClass(((JarURLConnection) resource.openConnection())
                        .getJarFile(), path));
            }
        }
        return classes;
    }

    private static List<Class> findClass(JarFile jarFile, String packagePath)
            throws ClassNotFoundException, URISyntaxException {
        List<Class> classes = new ArrayList<Class>();
        Enumeration<JarEntry> entrys = jarFile.entries();
        while (entrys.hasMoreElements()) {
            JarEntry jarEntry = entrys.nextElement();
            if (jarEntry.getName().startsWith(packagePath) && jarEntry.getName().endsWith(".class")) {
                Class implClass = Class.forName(jarEntry.getName().replace(SYMBOL.SLASH, SYMBOL.DOT).substring(0, jarEntry.getName().indexOf(SYMBOL.DOT)));
                if (!implClass.isInterface()) {
                    classes.add(implClass);
                }
            }
        }
        return classes;
    }

    private static List<Class> findClass(File directory, String packageName)
            throws ClassNotFoundException, URISyntaxException {
        List<Class> classes = new ArrayList<Class>();
        if (directory == null || !directory.exists()) {
            return null;
        }
        File[] fileList = directory.listFiles();
        if (CollectionsUtility.isNullOrEmpty(fileList)) {
            return null;
        }
        for (File file : fileList) {
            if (file.isDirectory()) {
                classes.addAll(findClass(file, packageName + SYMBOL.DOT + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + SYMBOL.DOT + file.getName().substring(0, file.getName().length() - 6)));
            }
        }

        return classes;
    }

    public static String getWrapClass(Class<?> basicType) {
        if (basicType.equals(int.class) || basicType.equals(Integer.class)) {
            return Integer.class.getName();
        }
        if (basicType.equals(byte.class) || basicType.equals(Byte.class)) {
            return Byte.class.getName();
        }
        if (basicType.equals(short.class) || basicType.equals(Short.class)) {
            return Short.class.getName();
        }
        if (basicType.equals(long.class) || basicType.equals(Long.class)) {
            return Long.class.getName();
        }
        if (basicType.equals(float.class) || basicType.equals(Float.class)) {
            return Float.class.getName();
        }
        if (basicType.equals(double.class) || basicType.equals(Double.class)) {
            return Double.class.getName();
        }
        if (basicType.equals(char.class) || basicType.equals(Character.class)) {
            return Character.class.getName();
        }
        if (basicType.equals(boolean.class) || basicType.equals(Boolean.class)) {
            return Boolean.class.getName();
        }
        if (basicType.equals(String.class)) {
            return String.class.getName();
        }
        if (basicType.equals(String[].class)) {
            return String.class.getName() + " []";
        }
        return basicType.getName();
    }

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ignore) {
        }
        if (cl == null) {
            cl = ClassUtility.class.getClassLoader();
            if (cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ignore) {
                }
            }
        }
        return cl;
    }

    static class MethodWithRank implements Comparable<MethodWithRank>{
        private Method method;
        private Float order;

        MethodWithRank(Method method, Float order) {
            this.method = method;
            this.order = order;
        }

        @Override
        public int compareTo(MethodWithRank o) {
            return this.order.compareTo(o.order);
        }
    }

    public static Method[] getOrderedMethod(Method[] methods) {
        List<MethodWithRank> methodList = new ArrayList<>();
        Method[] orderMethodArray=new Method[methods.length];
        for (Method m : methods) {
            if (m.getAnnotation(MethodOrder.class) != null) {
                Float order = m.getAnnotation(MethodOrder.class).order();
                methodList.add(new MethodWithRank(m, order));
            } else {
                methodList.add(new MethodWithRank(m, Float.MAX_VALUE));
            }
        }
        Collections.sort(methodList);
        for(int i=0;i<methods.length;i++){
            orderMethodArray[i]=methodList.get(i).method;
        }
        return orderMethodArray;
    }
}
