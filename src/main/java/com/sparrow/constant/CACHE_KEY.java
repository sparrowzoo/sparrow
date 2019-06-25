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

package com.sparrow.constant;

/**
 * 二级缓存Cache_Key
 *
 * @author harry
 * @version 1.0
 */
public class CACHE_KEY {
    /**
     * 保存system_config.properties配置参数
     */
    public static final String CONFIG_FILE = "CACHE_KEY_CONFIG_FILE";
    /**
     * 保存字典表配置参数
     */
    public static final String CONFIG_CODE_DB = "CACHE_KEY_CONFIG_CODE_DB";
    /**
     * 应用程序系统级业务缓存(应用程序级默认缓存)
     */
    public static final String DEFAULT = "CACHE_KEY_SYSTEM";

    /**
     * 国际化配置文件
     */
    public static final String INTERNATIONALIZATION = "CACHE_KEY_INTERNATIONALIZATION";

    /**
     * 日志
     */
    public static final String LOG = "LOG";

    public static final String DATA_SOURCE_URL_PAIR = "DATA_SOURCE_URL_PAIR";

}
