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
import com.sparrow.constant.CONFIG_KEY_LANGUAGE;
import com.sparrow.constant.USER;
import com.sparrow.cryptogram.Hmac;
import com.sparrow.cryptogram.ThreeDES;
import com.sparrow.protocol.LoginToken;
import com.sparrow.protocol.constant.CLIENT_INFORMATION;
import com.sparrow.utility.Config;
import com.sparrow.utility.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author harry
 */
public class LoginParser implements Serializable {
    private static final long serialVersionUID = -2215039934860669170L;
    static Logger logger = LoggerFactory.getLogger(LoginParser.class);

    public static LoginToken parse(String permission, String deviceId) {
        // 第一次请求时没有session id
        LoginToken login = new LoginToken();
        login.setUserId(USER.VISITOR_ID);
        login.setUserName(Config.getLanguageValue(
                CONFIG_KEY_LANGUAGE.USER_VISITOR,
                Config.getValue(CONFIG.LANGUAGE)));
        login.setAvatar(Config.getValue(CONFIG.DEFAULT_AVATAR));
        if (StringUtility.isNullOrEmpty(permission)) {
            return login;
        }

        try {
            String searchPermission = "&permission=";
            int permissionIndex = permission.lastIndexOf(searchPermission);
            if (permissionIndex < 0) {
                return login;
            }
            //id=%1$s&name=%2$s&login=%3$s&expireAt=%4$s&cent=%5$s&avatar=%6$s&deviceId=%7$s&activate=%8$s
            String userInfo = permission.substring(0, permissionIndex);
            String[] userInfoArray = userInfo.split("&");

            String dev = userInfoArray[6].substring("deviceId=".length());
            //设备不一致
            if (!dev.equals(deviceId)) {
                return login;
            }

            String expireAtStr = userInfoArray[3].substring("expireAt=".length());
            Long expireAt = 0L;
            //过期
            if (StringUtility.isNullOrEmpty(expireAtStr) && !"null".equalsIgnoreCase(expireAtStr)) {
                expireAt = Long.valueOf(expireAtStr);
                if (System.currentTimeMillis() > expireAt) {
                    return login;
                }
            }

            String signature = ThreeDES.getInstance().decrypt(
                    Config.getValue(USER.PASSWORD_3DAS_SECRET_KEY),
                    permission.substring(permissionIndex
                            + searchPermission.length()));
            String newSignature = Hmac.getInstance().getSHA1Base64(userInfo,
                    Config.getValue(USER.PASSWORD_SHA1_SECRET_KEY));

            //签名不一致
            if (signature == null || !signature.equals(newSignature)) {
                return login;
            }
            login.setUserId(Long.valueOf(userInfoArray[0].substring("id=".length())));
            login.setNickName(userInfoArray[1].substring("name="
                    .length()));
            login.setUserName(userInfoArray[2].substring("login=".length()));
            login.setCent(Long.valueOf(userInfoArray[4].substring("cent=".length())));
            login.setAvatar(userInfoArray[5].substring("avatar="
                    .length()));
            login.setDeviceId(dev);
            login.setExpireAt(expireAt);
            login.setAvatar(userInfoArray[7].substring("activate="
                    .length()));
            return login;
        } catch (Exception ignore) {
            logger.error("parser error ", ignore);
            return login;
        }
    }


    public static String getPermission(LoginToken login) {
        if (login.getUserName().equalsIgnoreCase(USER.ADMIN)) {
            login.setUserId(USER.ADMIN_ID);
        }
        String userInfo = String.format(
                "id=%1$s&name=%2$s&login=%3$s&expireAt=%4$s&cent=%5$s&avatar=%6$s&deviceId=%7$s&activate=%8$s",
                login.getUserId(),
                login.getUserName(),
                login.getNickName(),
                login.getExpireAt(),
                login.getCent(),
                login.getAvatar(),
                login.getDeviceId(),
                login.getActivate());
        String signature = Hmac.getInstance().getSHA1Base64(userInfo,
                Config.getValue(USER.PASSWORD_SHA1_SECRET_KEY));
        return userInfo
                + "&permission="
                + ThreeDES.getInstance().encrypt(
                Config.getValue(USER.PASSWORD_3DAS_SECRET_KEY),
                signature);
    }
}
