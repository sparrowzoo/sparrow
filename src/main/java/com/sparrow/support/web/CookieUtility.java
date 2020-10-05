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

package com.sparrow.support.web;

import com.sparrow.cache.CacheClient;
import com.sparrow.constant.CONFIG;
import com.sparrow.constant.cache.KEY;
import com.sparrow.constant.cache.key.KEY_USER;
import com.sparrow.exception.CacheConnectionException;
import com.sparrow.protocol.LoginToken;
import com.sparrow.protocol.constant.CLIENT_INFORMATION;
import com.sparrow.support.LoginParser;
import com.sparrow.utility.Config;
import com.sparrow.utility.JSUtility;
import com.sparrow.utility.StringUtility;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author harry
 */
public class CookieUtility {
    private static Logger logger = LoggerFactory.getLogger(CookieUtility.class);

    private CacheClient cacheClient;

    private KEY.Business permissionBusiness= KEY_USER.PERMISSION;

    public void setBackend(Boolean backend) {
        if (backend == null||!backend) {
            this.permissionBusiness = KEY_USER.PERMISSION;
            return;
        }
        this.permissionBusiness = KEY_USER.BACKEND_PERMISSION;
    }

    public void setCacheClient(CacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    public void set(HttpServletResponse response, String key,
                    String value, int days) {
        set(response, key, value, days, null);
    }

    public void setRoot(HttpServletResponse response, String key,
                        String value, int days) {
        String domain = Config.getValue(CONFIG.ROOT_DOMAIN);
        set(response, key, value, days, domain);
    }

    public void set(HttpServletResponse response, String key,
                    String value, int days, String domain) {
        if (StringUtility.isNullOrEmpty(domain)) {
            domain = Config.getValue(CONFIG.DOMAIN);
        }
        Cookie cookie = new Cookie(key, JSUtility.encodeURIComponent(value));
        if (domain != null) {
            cookie.setDomain(domain);
        } else {
            logger.warn("please config [domain] key in sparrow system config ");
        }
        cookie.setPath("/");
        if (days > 0) {
            cookie.setMaxAge(days * 24 * 60 * 60);
        }
        response.addCookie(cookie);
    }

    public String get(Cookie[] cookies, String key) {
        if (cookies == null || cookies.length == 0) {
            return null;
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(key)) {
                    return JSUtility.decodeURIComponent(cookie.getValue());
                }
            }
        }
        return null;
    }

    public LoginToken getUser(HttpServletRequest request) {
        final String sessionId = request.getSession().getId();
        String permission;
        KEY permissionKey = new KEY.Builder().business(permissionBusiness).businessId(sessionId).build();

        try {
            //如果支持redis,用户量较大时侯jvm 存储有压力，nginx ip_hash 配置
            if (cacheClient != null) {
                permission = cacheClient.string().get(permissionKey);
            } else {
                permission = (String) request.getSession().getAttribute(permissionKey.getBusiness());
            }
        } catch (CacheConnectionException e) {
            permission = (String) request.getSession().getAttribute(permissionKey.getBusiness());
        }

        if (StringUtility.isNullOrEmpty(permission)) {
            permission = this.get(request.getCookies(), permissionKey.getBusiness());
            if (!StringUtility.isNullOrEmpty(permission)) {
                if (cacheClient != null) {
                    try {
                        cacheClient.string().setExpire(permissionKey, 60 * 60, permission);
                    } catch (CacheConnectionException ignore) {
                        logger.error("cookie connection break", ignore);
                    }
                } else {
                    request.getSession().setAttribute(permissionKey.getBusiness(), permission);
                }
            }
        }
        String deviceId = request.getHeader(CLIENT_INFORMATION.DEVICE_ID);
        if (StringUtility.isNullOrEmpty(deviceId)) {
            deviceId = ServletUtility.getInstance().getClientIp(request);
        }
        return LoginParser.parse(permission, deviceId);
    }
}
