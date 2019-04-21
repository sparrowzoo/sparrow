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

package com.sparrow.support;

import com.sparrow.constant.CONFIG;
import com.sparrow.protocol.constant.CONSTANT;
import com.sparrow.protocol.OpenAuthAccount;
import com.sparrow.protocol.enums.PLATFORM;
import com.sparrow.utility.Config;

/**
 * @author harry
 */
public class OpenAuthAccountParser {

    /**
     * parse open auth protocol by config
     *
     * @param configKey platform_openplatform_appname
     *                  <p>
     *                  <p>
     *                  e.g..
     *                  pc_alipay_sparrow
     * @return
     * @see OpenAuthAccount getKey
     */
    public static OpenAuthAccount parse(String configKey) {
        OpenAuthAccount openAuthProtocol = new OpenAuthAccount();
        String[] keyArray = configKey.split("_");
        openAuthProtocol.setPlatform(PLATFORM.valueOf(keyArray[0].toUpperCase()));
        openAuthProtocol.setOpenPlatform(keyArray[1]);
        openAuthProtocol.setName(keyArray[2]);
        openAuthProtocol.setAppKey(getConfig(configKey, CONFIG.PLATFORM_APP_KEY));
        openAuthProtocol.setAppSecret(getConfig(configKey, CONFIG.PLATFORM_APP_SECRET));

        openAuthProtocol.setState(getConfig(configKey, CONFIG.PLATFORM_STATE));
        openAuthProtocol.setScope(getConfig(configKey, CONFIG.PLATFORM_SCOPE));
        openAuthProtocol.setPartner(getConfig(configKey, CONFIG.PLATFORM_PARTNER));
        openAuthProtocol.setSellerAccount(getConfig(configKey, CONFIG.PLATFORM_SELLER_ACCOUNT));
        openAuthProtocol.setPaySecret(getConfig(configKey, CONFIG.PLATFORM_PAY_SECRET));
        openAuthProtocol.setNotifyUrl(getConfig(configKey, CONFIG.PLATFORM_NOTIFY_URL));
        openAuthProtocol.setCallBackUrl(getConfig(configKey, CONFIG.PLATFORM_CALL_BACK_URL));

        if (!openAuthProtocol.getNotifyUrl().startsWith(CONSTANT.HTTP_PROTOCOL)) {
            openAuthProtocol.setNotifyUrl(Config.getValue(CONFIG.ROOT_PATH) + openAuthProtocol.getNotifyUrl());
        }

        if (!openAuthProtocol.getCallBackUrl().startsWith(CONSTANT.HTTP_PROTOCOL)) {
            openAuthProtocol.setCallBackUrl(Config.getValue(CONFIG.ROOT_PATH) + openAuthProtocol.getCallBackUrl());
        }

        openAuthProtocol.setCharset(getConfig(configKey, CONFIG.PLATFORM_CHARSET));
        return openAuthProtocol;
    }


    private static String getConfig(String prefix, String key) {
        return Config.getValue(prefix + "_" + key);
    }
}
