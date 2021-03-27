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

package com.sparrow.orm;

import com.sparrow.constant.Config;
import com.sparrow.support.EnvironmentSupport;
import com.sparrow.utility.StringUtility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author harry
 */
public class Dialect {

    private com.sparrow.enums.Dialect dialect;

    private static Map<String, Dialect> dialectMap = new HashMap<String, Dialect>();

    /**
     * 从配置文件poolName_default.properties 中获取dialect <p> driverClassName=org.gjt.mm.mysql.Driver username=root
     * password=123456 url=jdbc\:mysql\://127.0.0.1/db_name?autoReconnect=true&failOverReadOnly=false&useUnicode=true&characterEncoding=utf-8
     * poolSize=1 dialect=mysql
     *
     * @param schema
     * @return
     */
    public static Dialect getInstance(String schema) {
        if (dialectMap.containsKey(schema)) {
            return dialectMap.get(schema);
        }
        if (StringUtility.isNullOrEmpty(schema)) {
            schema = com.sparrow.utility.Config.getValue(Config.DEFAULT_DATA_SOURCE_KEY);
        }
        if (StringUtility.isNullOrEmpty(schema)) {
            schema = "sparrow";
        }
        Properties props = new Properties();
        String filePath = "/" + schema + "_default"
            + ".properties";
        try {
            props.load(EnvironmentSupport.getInstance().getFileInputStream(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String dialect = (String) props.get("dialect");
        if (StringUtility.isNullOrEmpty(dialect)) {
            dialect = "MYSQL";
        }
        Dialect d = new Dialect(com.sparrow.enums.Dialect.valueOf(dialect.toUpperCase()));
        dialectMap.put(schema, d);
        return d;
    }

    private Dialect(com.sparrow.enums.Dialect dialect) {
        this.dialect = dialect;
    }

    public String getOpenQuote() {
        switch (this.dialect) {
            case MYSQL:
                return "`";
            case SQL_SERVER:
                return "[";
            default:
                break;
        }
        return null;
    }

    public String getCloseQuote() {
        switch (this.dialect) {
            case MYSQL:
                return "`";
            case SQL_SERVER:
                return "]";
            default:
                break;
        }
        return null;
    }

    public com.sparrow.enums.Dialect getDialect() {
        return dialect;
    }
}
