/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"), you may not use this file except in compliance with
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

import com.sparrow.protocol.ErrorSupport;
import com.sparrow.protocol.ModuleSupport;

/**
 * first byte:
 * <p>
 * 0 表示系统模块
 * <p>
 * 1 模块错误
 * <p>
 * 2-3 bytes:
 * <p>
 * 00:全局模块(公共使用)
 * <p>
 * 01:用户模块
 * <p>
 * 02:EXCEL
 * <p>
 * 03:BLOG
 * <p>
 * 04:SHOP
 * <p>
 * 05:UPLOAD
 * <p>
 * 06:ACTIVITY
 * <p>
 * 4-5 bytes
 * <p>
 * 错误编码
 * <p>
 * <p>
 * 对于开发者和接口的调用者都隐藏着一个信息（当前操作的接口名称）
 *
 * @author harry 2013-11-9下午10:01:03
 */
public enum SPARROW_ERROR implements ErrorSupport {
    SYSTEM_SERVER_ERROR(true, SPARROW_MODULE.GLOBAL, "01", "System error"),
    SYSTEM_SERVICE_UNAVAILABLE(true, SPARROW_MODULE.GLOBAL, "02", "Service unavailable"),
    SYSTEM_REMOTE_SERVICE_UNAVAILABLE(true, SPARROW_MODULE.GLOBAL, "03", "Remote Service unavailable"),
    SYSTEM_PERMISSION_DENIED(true, SPARROW_MODULE.GLOBAL, "04", "Permission denied"),
    SYSTEM_ILLEGAL_REQUEST(true, SPARROW_MODULE.GLOBAL, "05", "Illegal request"),
    GLOBAL_DB_ADD_ERROR(true, SPARROW_MODULE.GLOBAL, "06", "add error"),
    GLOBAL_DB_DELETE_ERROR(true, SPARROW_MODULE.GLOBAL, "07", "delete error"),
    GLOBAL_DB_UPDATE_ERROR(true, SPARROW_MODULE.GLOBAL, "08", "update error"),
    GLOBAL_DB_LOAD_ERROR(true, SPARROW_MODULE.GLOBAL, "09", "load error"),
    GLOBAL_REQUEST_ID_NOT_EXIST(true, SPARROW_MODULE.GLOBAL, "11", "Request id not exist"),
    GLOBAL_VALIDATE_CODE_ERROR(true, SPARROW_MODULE.GLOBAL, "12", "ValidateCode error"),
    GLOBAL_CONTENT_IS_NULL(true, SPARROW_MODULE.GLOBAL, "13", "Content is null"),
    GLOBAL_CONTAIN_ILLEGAL_WEBSITE(true, SPARROW_MODULE.GLOBAL, "14", "Contain illegal website"),
    GLOBAL_CONTAIN_ADVERTISING(true, SPARROW_MODULE.GLOBAL, "15", "Contain advertising"),
    GLOBAL_CONTENT_IS_ILLEGAL(true, SPARROW_MODULE.GLOBAL, "16", "Content is illegal"),
    GLOBAL_CONTENT_DUPLICATE(true, SPARROW_MODULE.GLOBAL, "17", "Content duplicate"),
    GLOBAL_UNSUPPORTED_IMAGE_TYPE(true, SPARROW_MODULE.GLOBAL, "18",
            "Unsupported image type only support JPG, GIF, PNG"),
    GLOBAL_IMAGE_SIZE_TOO_LARGE(true, SPARROW_MODULE.GLOBAL, "19", "Image size too large"),
    GLOBAL_ACCOUNT_ILLEGAL(true, SPARROW_MODULE.GLOBAL, "20",
            "Account or ip or app is illegal, can not continue"),
    GLOBAL_OUT_OF_TIMES_LIMIT(true, SPARROW_MODULE.GLOBAL, "21", "Out of times limit"),
    GLOBAL_ADMIN_CAN_NOT_OPERATION(true, SPARROW_MODULE.GLOBAL, "22", "Admin can't operation"),
    GLOBAL_PARAMETER_NULL(true, SPARROW_MODULE.GLOBAL, "23", "Parameter is null"),
    GLOBAL_REQUEST_REPEAT(true, SPARROW_MODULE.GLOBAL, "24", "Request repeat"),
    GLOBAL_EMAIL_SEND_FAIL(true, SPARROW_MODULE.GLOBAL, "25", "email send fail"),
    GLOBAL_OPERATION_VALIDATE_STATUS_INVALID(true, SPARROW_MODULE.GLOBAL, "26", "operation validate status is invalid"),
    GLOBAL_OPERATION_VALIDATE_ROLE_INVALID(true, SPARROW_MODULE.GLOBAL, "27", "operation validate role is invalid"),
    GLOBAL_PARAMETER_IS_ILLEGAL(true, SPARROW_MODULE.GLOBAL, "28", "parameter is illegal"),
    GLOBAL_SMS_SEND_ERROR(true, SPARROW_MODULE.GLOBAL, "29", "short message service error"),
    USER_NAME_EXIST(false, SPARROW_MODULE.USER, "01", "User name exist"),
    USER_EMAIL_EXIST(false, SPARROW_MODULE.USER, "02", "User email exist"),
    USER_MOBILE_EXIST(false, SPARROW_MODULE.USER, "03", "User mobile exist"),
    USER_OLD_PASSWORD_ERROR(false, SPARROW_MODULE.USER, "04", "User old password error"),
    USER_NAME_NOT_EXIST(false, SPARROW_MODULE.USER, "05", "Username not exist"),
    USER_MOBILE_NOT_EXIST(false, SPARROW_MODULE.USER, "06", "Mobile not exist"),
    USER_EMAIL_NOT_EXIST(false, SPARROW_MODULE.USER, "07", "User email not exist"),
    USER_PASSWORD_ERROR(false, SPARROW_MODULE.USER, "08", "User password error"),
    USER_PASSWORD_FORMAT_ERROR(false, SPARROW_MODULE.USER, "09", "User password format error"),
    USER_DISABLED(false, SPARROW_MODULE.USER, "10", "User disabled"),
    USER_NOT_ACTIVATE(false, SPARROW_MODULE.USER, "11", "User not activate"),
    USER_PASSWORD_VALIDATE_TOKEN_ERROR(false, SPARROW_MODULE.USER, "12", "user password validate_token error"),
    USER_VALIDATE_TIME_OUT(false, SPARROW_MODULE.USER, "13", "user validate_code time out"),
    USER_VALIDATE_NOT_EXIST(false, SPARROW_MODULE.USER, "14", "user validate_code not exist"),
    USER_VALIDATE_VALID(false, SPARROW_MODULE.USER, "15", "user validate code valid"),
    USER_VALIDATE_TOKEN_TIME_OUT(false, SPARROW_MODULE.USER, "16", "user validate token time out"),
    USER_REGISTER_NAME_NULL(false, SPARROW_MODULE.USER, "17", "user name can't be null"),
    USER_REGISTER_MOBILE_NULL(false, SPARROW_MODULE.USER, "18", "user mobile can't be null"),
    USER_REGISTER_EMAIL_NULL(false, SPARROW_MODULE.USER, "19", "user email can't be null"),
    USER_AVATAR_NULL(false, SPARROW_MODULE.USER, "20", "user avatar can't be null"),
    USER_AVATAR_CUT_COORDINATE_NULL(false, SPARROW_MODULE.USER, "21", "user avatar cut coordinate can't be null"),
    USER_NOT_LOGIN(false, SPARROW_MODULE.USER, "22", "user not login"),

    //blog
    BLOG_NOT_THREAD_EDIT_PRIVILEGE(false, SPARROW_MODULE.BLOG, "01", "No edit privilege"),
    BLOG_THREAD_ID_NOT_EXIST(false, SPARROW_MODULE.BLOG, "02", "thread id not exist"),
    BLOG_FORUM_CODE_NULL(false, SPARROW_MODULE.BLOG, "03", "thread forum code is null"),
    BLOG_THREAD_SIMHASH_EXIST(false, SPARROW_MODULE.BLOG, "04", "thread simhash code has exist"),
    BLOG_THREAD_CRAWLED(false, SPARROW_MODULE.BLOG, "05", "thread has crawled"),
    BLOG_LOCK(false, SPARROW_MODULE.BLOG, "06", "thread can't operation"),

    //shop
    SHOP_PRODUCT_NOT_EXIST(false, SPARROW_MODULE.SHOP, "01", "shop product not exist"),
    SHOP_DATE_NOT_ALLOW(false, SPARROW_MODULE.SHOP, "02", "shop date not allow"),
    SHOP_DATE_NOT_EXIST(false, SPARROW_MODULE.SHOP, "03", "shop date not exist"),
    SHOP_ALLOW_NUM_OUT(false, SPARROW_MODULE.SHOP, "04", "shop allow num out"),
    SHOP_PAY_NOTIFY_ID_ERROR(false, SPARROW_MODULE.SHOP, "05", "pay notify id error"),
    SHOP_PAY_SIGNATURE_ERROR(false, SPARROW_MODULE.SHOP, "06", "pay signature error"),
    SHOP_ORDER_NOT_EXIST(false, SPARROW_MODULE.SHOP, "07", "order not exist"),
    SHOP_ORDER_STATUS_ERROR(false, SPARROW_MODULE.SHOP, "08", "order status error"),
    SHOP_PAY_STATUS_ERROR(false, SPARROW_MODULE.SHOP, "09", "pay status error"),

    //upload
    UPLOAD_SERVICE_ERROR(false, SPARROW_MODULE.UPLOAD, "01", "upload service error"),
    UPLOAD_OUT_OF_SIZE(false, SPARROW_MODULE.UPLOAD, "02", "upload out of size"),
    UPLOAD_FILE_NAME_NULL(false, SPARROW_MODULE.UPLOAD, "03", "upload file name null"),
    UPLOAD_FILE_TYPE_ERROR(false, SPARROW_MODULE.UPLOAD, "04", "upload file type error"),
    UPLOAD_SRC_DESC_PATH_REPEAT(false, SPARROW_MODULE.UPLOAD, "05", "upload src desc  path repeat"),

    FILE_NOT_FOUND(false, SPARROW_MODULE.ATTACH, "01", "file not found"),
    FILE_CAN_NOT_READ(false, SPARROW_MODULE.ATTACH, "02", "io error file can't read"),
    IMAGE_EXTENSION_NOT_FOUND(true,SPARROW_MODULE.ATTACH, "03", "[%s] image extension  not found "),

    //活动
    ACTIVITY_SCAN_TOKEN_TIME_OUT(false, SPARROW_MODULE.ACTIVITY, "01", "activity scan token time out"),

    ACTIVITY_TIMES_OUT(false, SPARROW_MODULE.ACTIVITY, "02", "activity time out"),

    ACTIVITY_RULE_GIFT_TIMES_OUT(false, SPARROW_MODULE.ACTIVITY, "03", "activity gift time out");

    private boolean system;
    private ModuleSupport module;
    private String code;
    private String message;

    SPARROW_ERROR(boolean system, ModuleSupport module, String code, String message) {
        this.system = system;
        this.message = message;
        this.module = module;
        this.code = (system ? 0 : 1) + module.code() + code;
    }

    @Override
    public boolean system() {
        return this.system;
    }

    @Override
    public ModuleSupport module() {
        return this.module;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
