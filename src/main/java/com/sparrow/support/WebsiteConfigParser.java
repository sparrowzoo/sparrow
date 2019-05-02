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

import com.sparrow.constant.CONFIG_KEY_DB;
import com.sparrow.protocol.constant.CONSTANT;
import com.sparrow.protocol.WebsiteConfig;
import com.sparrow.utility.Config;
import com.sparrow.utility.StringUtility;

import java.util.Map;

/**
 * @author harry
 */
public class WebsiteConfigParser {


    public static WebsiteConfig parse(Map<String, String> websiteConfigMap) {
        WebsiteConfig websiteConfig=new WebsiteConfig();
        if (websiteConfigMap != null && websiteConfigMap.size() > 0) {
           websiteConfig.setTitle(websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT + "-"
                    + CONFIG_KEY_DB.WEBSITE_CONFIG.TITLE));
            websiteConfig.setDescription(websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT
                    + "-" + CONFIG_KEY_DB.WEBSITE_CONFIG.DESCRIPTION));


            websiteConfig.setKeywords(websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT
                    + "-" + CONFIG_KEY_DB.WEBSITE_CONFIG.KEYWORDS));
            websiteConfig.setContact(websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT + "-"
                    + CONFIG_KEY_DB.WEBSITE_CONFIG.CONTACT));
            websiteConfig.setBanner(websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT + "-"
                    + CONFIG_KEY_DB.WEBSITE_CONFIG.BANNER));

            websiteConfig.setIcp(websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT + "-"
                    + CONFIG_KEY_DB.WEBSITE_CONFIG.ICP));

            websiteConfig.setBannerFlash(websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT
                    + "-" + CONFIG_KEY_DB.WEBSITE_CONFIG.BANNER_FLASH));
            websiteConfig.setLogo(websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT + "-"
                    + CONFIG_KEY_DB.WEBSITE_CONFIG.LOGO));
            return websiteConfig;
        }
        String configPrefix = "web_config_";
        websiteConfig.setTitle(Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.TITLE.toLowerCase()));
        websiteConfig.setDescription(Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.DESCRIPTION.toLowerCase()));
        websiteConfig.setKeywords(Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.KEYWORDS.toLowerCase()));
        websiteConfig.setContact(Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.CONTACT.toLowerCase()));
        String banner=Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.BANNER.toLowerCase());
        websiteConfig.setIcp(Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.ICP.toLowerCase()));
        String bannerFlash=Config.getLanguageValue(configPrefix + StringUtility.humpToLower(CONFIG_KEY_DB.WEBSITE_CONFIG.BANNER_FLASH));
        String logo = Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.LOGO.toLowerCase());
        websiteConfig.setLogo(StringUtility.replace(logo, CONSTANT.REPLACE_MAP));
        websiteConfig.setBanner(StringUtility.replace(banner, CONSTANT.REPLACE_MAP));
        websiteConfig.setBannerFlash(StringUtility.replace(bannerFlash, CONSTANT.REPLACE_MAP));
        return websiteConfig;
    }

}
