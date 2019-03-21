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

package com.sparrow.support.mobile;

import com.sparrow.constant.CONFIG;
import com.sparrow.constant.CONFIG_KEY_LANGUAGE;
import com.sparrow.constant.SPARROW_ERROR;
import com.sparrow.enums.ERROR_FIELD_SUFFIX;
import com.sparrow.protocol.BusinessException;
import com.sparrow.utility.Config;

/**
 * MobileEntity <p> session 的key就是当前mobile
 *
 * @author harry
 */
public class MobileEntity {

    public MobileEntity(String mobile, String templateId) {
        this.setCompanyName(Config.getLanguageValue(CONFIG_KEY_LANGUAGE.MOBILE_COMPANY));
        this.setKey(Config.getValue(CONFIG.MOBILE_KEY));
        this.setMobile(mobile);
        this.setTemplateId(templateId);
    }

    public MobileEntity() {
    }

    private String key;
    private String mobile;
    private String templateId;
    private String templateValue;
    private String extend;
    private String userId;
    private String companyName;
    private String validateCode;

    private Long sendTime;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateValue() {
        return templateValue;
    }

    public void setTemplateValue(String templateValue) {
        this.templateValue = templateValue;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 验证码是否有效
     *
     * @param field 验证码显示域
     * @return 验证码是否有效
     */
    public Boolean valid(ERROR_FIELD_SUFFIX field) throws BusinessException {
        //手机验证码有效时间
        int mobileValidateTokenAvailableTime =Config
                .getIntegerValue(CONFIG.MOBILE_VALIDATE_TOKEN_AVAILABLE_TIME);
        Long currentTime = System.currentTimeMillis();
        Long validTime = this.sendTime + mobileValidateTokenAvailableTime * 1000;
        if (currentTime > validTime) {
            throw new BusinessException(SPARROW_ERROR.USER_VALIDATE_TIME_OUT, field.name());
        }
        return true;
    }

    /**
     * 验证码是否正确
     *
     * @param validateCode 验证码
     * @param field        验证码显示域
     * @return 是否验证成功
     */
    public Boolean validate(String validateCode, ERROR_FIELD_SUFFIX field) throws BusinessException {
        if (Config.getBooleanValue(CONFIG.DEBUG)) {
            return true;
        }
        Boolean result = valid(field);
        if (!result) {
            return result;
        }
        if (!validateCode.equals(this.validateCode)) {
            throw new BusinessException(SPARROW_ERROR.GLOBAL_VALIDATE_CODE_ERROR, field.name());
        }
        return true;
    }
}
