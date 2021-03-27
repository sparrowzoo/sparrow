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

import com.sparrow.constant.Config;
import com.sparrow.protocol.constant.CONSTANT;
import com.sparrow.protocol.constant.EXTENSION;
import com.sparrow.protocol.constant.magic.SYMBOL;
import com.sparrow.utility.StringUtility;

import java.util.Enumeration;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author harry
 */
public class ServletUtility {

    private static final ServletUtility INSTANCE = new ServletUtility();

    private ServletUtility() {
    }

    public static ServletUtility getInstance() {
        return INSTANCE;
    }

    public boolean include(ServletRequest request) {
        return request
                .getAttribute(CONSTANT.REQUEST_ACTION_INCLUDE) != null;
    }


    public String assembleActualUrl(String url) {
        //rootPath is null when contain init ...
        String rootPath = com.sparrow.utility.Config.getValue(Config.ROOT_PATH);
        if (rootPath != null && url.startsWith(rootPath)) {
            url = url.substring(rootPath.length());
        }
        if (!url.startsWith(SYMBOL.SLASH)) {
            url = SYMBOL.SLASH + url;
        }
        String extension = com.sparrow.utility.Config.getValue(Config.DEFAULT_PAGE_EXTENSION, EXTENSION.JSP);
        String pagePrefix = com.sparrow.utility.Config.getValue(Config.DEFAULT_PAGE_PREFIX, "/template");
        return pagePrefix + url + extension;
    }

    public String getActionKey(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Object servletPath = request
                .getAttribute(CONSTANT.REQUEST_ACTION_INCLUDE);
        String actionKey;
        if (servletPath != null) {
            actionKey = servletPath.toString();
        } else {
            actionKey = httpServletRequest.getServletPath();
        }

        String rootPath = com.sparrow.utility.Config.getValue(Config.ROOT_PATH);
        if (!StringUtility.isNullOrEmpty(rootPath)) {
            return actionKey;
        }

        //第一次请求时初始化rootPath website  和domain
        String serverName = request.getServerName();
        String path = httpServletRequest.getContextPath();
        rootPath = request.getScheme()
                + "://"
                + serverName
                + (request.getServerPort() == 80 ? "" : ":"
                + request.getServerPort()) + path;
        // eclipse tomcat 启动时会默认请求http://localhost故此处加此判断
        //只解析一二级域名 http://www.sparrowzoo.com
        if (rootPath.indexOf(CONSTANT.LOCALHOST) != 0 && rootPath.indexOf(CONSTANT.LOCALHOST_127) != 0) {
            String website = serverName.substring(serverName.indexOf(".") + 1);
            website = website.substring(0, website.indexOf("."));
            com.sparrow.utility.Config.resetKey(Config.WEBSITE, website);

            String rootDomain = com.sparrow.utility.Config.getValue(Config.ROOT_DOMAIN);
            if (rootDomain == null) {
                com.sparrow.utility.Config.resetKey(Config.ROOT_DOMAIN, serverName.substring(serverName.indexOf(".")));
            }

            String domain = com.sparrow.utility.Config.getValue(Config.DOMAIN);
            if (domain == null) {
                com.sparrow.utility.Config.resetKey(Config.DOMAIN,
                        serverName);
            }
            CONSTANT.REPLACE_MAP.put("$website", website);
        }
        com.sparrow.utility.Config.resetKey(Config.ROOT_PATH, rootPath);
        return actionKey;
    }

    public String getClientIp(ServletRequest request) {
        if (request == null) {
            return CONSTANT.LOCALHOST;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ip = httpRequest.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpRequest.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpRequest.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.indexOf(SYMBOL.COLON) > 0) {
            ip = ip.split(SYMBOL.COLON)[0];
        }
        return ip;
    }

    public String getAllParameter(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<?> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = request.getParameter(key);
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(key);
            sb.append(":");
            sb.append(value);
        }
        return sb.toString();
    }

    public String referer(HttpServletRequest request) {
        return request.getHeader("Referer");
    }
}
