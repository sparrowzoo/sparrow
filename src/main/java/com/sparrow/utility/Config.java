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

import com.sparrow.constant.CACHE_KEY;
import com.sparrow.constant.CONFIG;
import com.sparrow.core.cache.Cache;
import com.sparrow.core.cache.StrongDurationCache;
import com.sparrow.protocol.constant.CONSTANT;
import com.sparrow.protocol.constant.magic.SYMBOL;
import com.sparrow.support.EnvironmentSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author harry
 */
public class Config {
    private static Logger logger = LoggerFactory.getLogger(Config.class);
    private static Cache<String, String> configCache;
    private static Cache<String, Map<String, String>> internationalization;

    static {
        configCache = new StrongDurationCache<>(CACHE_KEY.CONFIG_FILE);
        internationalization = new StrongDurationCache<>(CACHE_KEY.INTERNATIONALIZATION);
    }

    public static String getLanguageValue(String propertiesKey) {
        String language = getValue(CONFIG.LANGUAGE);
        return getLanguageValue(propertiesKey, language);
    }

    public static String getLanguageValue(String key, String language) {
        return getLanguageValue(key, language, SYMBOL.EMPTY);
    }

    private static String defaultOrEmpty(String defaultValue) {
        return StringUtility.isNullOrEmpty(defaultValue) ? SYMBOL.EMPTY : defaultValue;
    }

    public static String getLanguageValue(String key, String language, String defaultValue) {
        if (StringUtility.isNullOrEmpty(language)) {
            language = getValue(CONFIG.LANGUAGE);
        } else {
            language = language.toLowerCase();
        }

        if (internationalization == null) {
            return defaultOrEmpty(defaultValue);
        }

        Map<String, String> internationalizationMap = internationalization
                .get(language);
        if (internationalizationMap == null) {
            return defaultOrEmpty(defaultValue);
        }
        String value = internationalizationMap.get(key.toLowerCase());
        if (value == null) {
            return defaultOrEmpty(defaultValue);
        }
        String rootPath = Config.getValue(CONFIG.ROOT_PATH);
        if (!StringUtility.isNullOrEmpty(rootPath) && value.contains(SYMBOL.DOLLAR + CONFIG.ROOT_PATH)) {
            value = value.replace(SYMBOL.DOLLAR + CONFIG.ROOT_PATH, rootPath);
        }
        if (StringUtility.isNullOrEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    public static Map<String, String> load(InputStream stream, String charset) {
        if (stream == null) {
            return null;
        }
        Map<String, String> systemMessage = new ConcurrentHashMap<String, String>();
        Properties props = new Properties();

        try {
            props.load(stream);
        } catch (IOException e) {
            logger.error("load config file error", e);
            return null;
        }

        for (Object key : props.keySet()) {
            String strKey = key.toString();
            String value = props.getProperty(strKey);
            if (StringUtility.isNullOrEmpty(charset)) {
                charset = CONSTANT.CHARSET_UTF_8;
            }
            try {
                value = new String(value.getBytes(CONSTANT.CHARSET_ISO_8859_1), charset);
            } catch (UnsupportedEncodingException ignore) {
            }
            if (value.startsWith("${") && value.endsWith("}")) {
                String envKey = value.substring(2, value.length() - 1).toUpperCase();
                String envValue = System.getenv(envKey);
                if (envValue == null) {
                    logger.warn("{} not found,please config env", envKey);
                    continue;
                }
                value = envValue;
            }
            systemMessage.put(strKey, value);
        }
        return systemMessage;
    }

    public static Map<String, String> load(InputStream stream) {
        return load(stream, null);
    }

    public static Map<String, String> loadFromClassesPath(String configFilePath) {
        InputStream stream = null;
        try {
            stream = EnvironmentSupport.getInstance().getFileInputStream(configFilePath);
        } catch (FileNotFoundException e) {
            logger.error("{} file not found", configFilePath);
            return null;
        }
        if (stream == null) {
            return null;
        }
        return load(stream);
    }

    public static Map<String, String> loadFromClassesPath(String configFilePath, String charset) {
        InputStream stream = null;
        try {
            stream = EnvironmentSupport.getInstance().getFileInputStream(configFilePath);
        } catch (FileNotFoundException e) {
            logger.error("[{}] file not found ", configFilePath);
            return null;
        }
        return load(stream, charset);
    }

    public static void initSystem(String configFilePath) {
        Map<String, String> systemMessage = loadFromClassesPath(configFilePath);
        if (systemMessage == null) {
            return;
        }
        configCache.putAll(systemMessage);
        if (systemMessage.get(CONFIG.RESOURCE_PHYSICAL_PATH) != null) {
            CONSTANT.REPLACE_MAP.put("$physical_resource", systemMessage.get(CONFIG.RESOURCE_PHYSICAL_PATH));
        }
        if (systemMessage.get(CONFIG.RESOURCE) != null) {
            CONSTANT.REPLACE_MAP.put("$resource", systemMessage.get(CONFIG.RESOURCE));
        }
        if (systemMessage.get(CONFIG.IMAGE_WEBSITE) != null) {
            CONSTANT.REPLACE_MAP.put("$image_website", systemMessage.get(CONFIG.IMAGE_WEBSITE));
        }
        logger.info("==========system config init============");
    }

    public static void initInternationalization(String language) {
        if (StringUtility.isNullOrEmpty(language)) {
            language = getValue(CONFIG.LANGUAGE);
        }
        Map<String, String> properties = loadFromClassesPath("/messages_"
                + language
                + ".properties", CONSTANT.CHARSET_UTF_8);
        if (properties != null) {
            internationalization.put(language, properties);
        }
    }

    public static String getValue(String key) {
        return getValue(key, null);
    }

    public static String getValue(String key, String defaultValue) {
        try {
            Object value = configCache.get(key);
            if (value == null) {
                return defaultValue;
            }
            String v = value.toString();
            v = StringUtility.replace(v, CONSTANT.REPLACE_MAP);
            return v;
        } catch (Exception e) {
            logger.error("get value error", e);
        }
        //不存在 并不等于""
        return null;
    }

    public static boolean getBooleanValue(String config) {
        String value = getValue(config);
        return !StringUtility.isNullOrEmpty(value) && Boolean.TRUE.toString().equalsIgnoreCase(value);
    }

    public static boolean getBooleanValue(String config, boolean defaultValue) {
        String value = getValue(config);
        if (StringUtility.isNullOrEmpty(value)) {
            return defaultValue;
        }
        return Boolean.TRUE.toString().equalsIgnoreCase(value);
    }

    public static Integer getIntegerValue(String config) {
        String value = Config.getValue(config);
        if (StringUtility.isNullOrEmpty(value)) {
            return 0;
        }
        return Integer.valueOf(value);
    }

    public static void resetKey(String key, String website) {
        configCache.put(key, website);
    }
}
