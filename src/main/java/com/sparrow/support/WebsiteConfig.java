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
import com.sparrow.constant.CONSTANT;
import com.sparrow.core.spi.JsonFactory;
import com.sparrow.protocol.Entity;
import com.sparrow.utility.Config;
import com.sparrow.utility.StringUtility;

import java.util.Map;

/**
 * @author harry
 */
public class WebsiteConfig implements Entity {
    private static final long serialVersionUID = -214177209049269222L;
    private String title;
    private String keywords;
    private String description;
    private String logo;
    private String banner;
    private String bannerFlash;
    private String icp;
    private String contact;

    public WebsiteConfig() {
    }

    public WebsiteConfig(Map<String, String> websiteConfigMap) {
        if (websiteConfigMap != null && websiteConfigMap.size() > 0) {
            this.title = websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT + "-"
                    + CONFIG_KEY_DB.WEBSITE_CONFIG.TITLE);
            this.description = websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT
                    + "-" + CONFIG_KEY_DB.WEBSITE_CONFIG.DESCRIPTION);
            this.keywords = websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT
                    + "-" + CONFIG_KEY_DB.WEBSITE_CONFIG.KEYWORDS);
            this.contact = websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT + "-"
                    + CONFIG_KEY_DB.WEBSITE_CONFIG.CONTACT);
            this.banner = websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT + "-"
                    + CONFIG_KEY_DB.WEBSITE_CONFIG.BANNER);
            this.icp = websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT + "-"
                    + CONFIG_KEY_DB.WEBSITE_CONFIG.ICP);
            this.bannerFlash = websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT
                    + "-" + CONFIG_KEY_DB.WEBSITE_CONFIG.BANNER_FLASH);
            this.logo = websiteConfigMap.get(CONFIG_KEY_DB.WEBSITE_CONFIG_PARENT + "-"
                    + CONFIG_KEY_DB.WEBSITE_CONFIG.LOGO);
            return;
        }
        String configPrefix = "web_config_";
        this.title = Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.TITLE.toLowerCase());
        this.description = Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.DESCRIPTION.toLowerCase());
        this.keywords = Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.KEYWORDS.toLowerCase());
        this.contact = Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.CONTACT.toLowerCase());
        this.banner = Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.BANNER.toLowerCase());
        this.icp = Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.ICP.toLowerCase());
        this.bannerFlash = Config.getLanguageValue(configPrefix + StringUtility.humpToLower(CONFIG_KEY_DB.WEBSITE_CONFIG.BANNER_FLASH));
        this.logo = Config.getLanguageValue(configPrefix + CONFIG_KEY_DB.WEBSITE_CONFIG.LOGO.toLowerCase());
        this.logo = StringUtility.replace(this.logo, CONSTANT.REPLACE_MAP);
        this.banner = StringUtility.replace(this.banner, CONSTANT.REPLACE_MAP);
        this.bannerFlash = StringUtility.replace(this.bannerFlash, CONSTANT.REPLACE_MAP);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    @Override
    public String toString() {
        return JsonFactory.getProvider().toString(this);
    }

    public String getBannerFlash() {
        return bannerFlash;
    }

    public void setBannerFlash(String bannerFlash) {
        this.bannerFlash = bannerFlash;
    }

    public String getIcp() {
        return icp;
    }

    public void setIcp(String icp) {
        this.icp = icp;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
