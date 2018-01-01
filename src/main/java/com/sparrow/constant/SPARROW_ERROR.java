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

import com.sparrow.protocol.ModuleSupport;
import com.sparrow.support.AbstractErrorSupport;

/**
 * first byte:
 *
 * 1 表示系统模块
 *
 * 2 模块错误
 *
 * 2-3 bytes:
 *
 * 00:全局模块(公共使用)
 *
 * 01:用户模块
 *
 * 02:EXCEL
 *
 * 03:BLOG
 *
 * 04:SHOP
 *
 * 05:UPLOAD
 *
 * 06:ACTIVITY
 *
 * 4-5 bytes
 *
 * 错误编码
 *
 *
 * 对于开发者和接口的调用者都隐藏着一个信息（当前操作的接口名称）
 *
 * @author harry 2013-11-9下午10:01:03
 */
public class SPARROW_ERROR extends AbstractErrorSupport {

    public static final SPARROW_ERROR SYSTEM_SERVER_ERROR = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "01", "System error");

    public static final SPARROW_ERROR SYSTEM_SERVICE_UNAVAILABLE = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "02", "Service unavailable");

    public static final SPARROW_ERROR SYSTEM_REMOTE_SERVICE_UNAVAILABLE = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "03", "Remote Service unavailable");

    public static final SPARROW_ERROR SYSTEM_PERMISSION_DENIED = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "04", "Permission denied");

    public static final SPARROW_ERROR SYSTEM_ILLEGAL_REQUEST = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "05", "Illegal request");

    public static final SPARROW_ERROR GLOBAL_DB_ADD_ERROR = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "06", "add error");

    public static final SPARROW_ERROR GLOBAL_DB_DELETE_ERROR = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "07", "delete error");

    public static final SPARROW_ERROR GLOBAL_DB_UPDATE_ERROR = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "08", "update error");

    public static final SPARROW_ERROR GLOBAL_DB_LOAD_ERROR = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "09", "load error");

    public static final SPARROW_ERROR GLOBAL_REQUEST_ID_NOT_EXIST = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "01", "Request id not exist");

    public static final SPARROW_ERROR GLOBAL_VALIDATE_CODE_ERROR = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "02", "ValidateCode error");

    public static final SPARROW_ERROR GLOBAL_CONTENT_IS_NULL = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "03", "Content is null");

    public static final SPARROW_ERROR GLOBAL_CONTAIN_ILLEGAL_WEBSITE = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "04", "Contain illegal website");

    public static final SPARROW_ERROR GLOBAL_CONTAIN_ADVERTISING = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "05", "Contain advertising");

    public static final SPARROW_ERROR GLOBAL_CONTENT_IS_ILLEGAL = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "06", "Content is illegal");

    public static final SPARROW_ERROR GLOBAL_CONTENT_REPEAT = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "07", "Repeat content");

    public static final SPARROW_ERROR GLOBAL_UNSUPPORTED_IMAGE_TYPE = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "08",
        "Unsupported image type only support JPG, GIF, PNG");

    public static final SPARROW_ERROR GLOBAL_IMAGE_SIZE_TOO_LARGE = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "09", "Image size too large");

    public static final SPARROW_ERROR GLOBAL_ACCOUNT_ILLEGAL = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "10",
        "Account or ip or app is illegal, can not continue");

    public static final SPARROW_ERROR GLOBAL_OUT_OF_TIMES_LIMIT = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "11", "Out of times limit");

    public static final SPARROW_ERROR GLOBAL_ADMIN_CAN_NOT_OPERATION = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "12", "Admin can't operation");

    public static final SPARROW_ERROR GLOBAL_PARAMETER_NULL = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "13", "Parameter is null");

    public static final SPARROW_ERROR GLOBAL_REQUEST_REPEAT = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "14", "Request repeat");

    public static final SPARROW_ERROR GLOBAL_EMAIL_SEND_FAIL = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "15", "email send fail");

    public static final SPARROW_ERROR GLOBAL_OPERATION_VALIDATE_STATUS_INVALID = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "16", "operation validate status is invalid");

    public static final SPARROW_ERROR GLOBAL_OPERATION_VALIDATE_ROLE_INVALID = new SPARROW_ERROR(true, SPARROW_MODULE.GLOBAL, "17", "operation validate role is invalid");

    public static final SPARROW_ERROR USER_NAME_EXIST = new SPARROW_ERROR(SPARROW_MODULE.USER, "01", "User name exist");

    public static final SPARROW_ERROR USER_EMAIL_EXIST = new SPARROW_ERROR(SPARROW_MODULE.USER, "02", "User email exist");

    public static final SPARROW_ERROR USER_MOBILE_EXIST = new SPARROW_ERROR(SPARROW_MODULE.USER, "03", "User mobile exist");

    public static final SPARROW_ERROR USER_OLD_PASSWORD_ERROR = new SPARROW_ERROR(SPARROW_MODULE.USER, "04", "User old password error");

    public static final SPARROW_ERROR USER_NAME_NOT_EXIST = new SPARROW_ERROR(SPARROW_MODULE.USER, "05", "Username not exist");

    public static final SPARROW_ERROR USER_MOBILE_NOT_EXIST = new SPARROW_ERROR(SPARROW_MODULE.USER, "06", "Mobile not exist");

    public static final SPARROW_ERROR USER_EMAIL_NOT_EXIST = new SPARROW_ERROR(SPARROW_MODULE.USER, "07", "User email not exist");

    public static final SPARROW_ERROR USER_PASSWORD_ERROR = new SPARROW_ERROR(SPARROW_MODULE.USER, "08", "User password error");

    public static final SPARROW_ERROR USER_PASSWORD_FORMAT_ERROR = new SPARROW_ERROR(SPARROW_MODULE.USER, "09", "User password format error");

    public static final SPARROW_ERROR USER_DISABLED = new SPARROW_ERROR(SPARROW_MODULE.USER, "10", "User disabled");

    public static final SPARROW_ERROR USER_NOT_ACTIVATE = new SPARROW_ERROR(SPARROW_MODULE.USER, "11", "User not activate");

    public static final SPARROW_ERROR USER_PASSWORD_VALIDATE_TOKEN_ERROR = new SPARROW_ERROR(SPARROW_MODULE.USER, "12", "user password validate_token error");

    public static final SPARROW_ERROR USER_VALIDATE_TIME_OUT = new SPARROW_ERROR(SPARROW_MODULE.USER, "13", "user validate_code time out");

    public static final SPARROW_ERROR USER_VALIDATE_NOT_EXIST = new SPARROW_ERROR(SPARROW_MODULE.USER, "14", "user validate_code not exist");

    public static final SPARROW_ERROR USER_VALIDATE_VALID = new SPARROW_ERROR(SPARROW_MODULE.USER, "15", "user validate code valid");

    public static final SPARROW_ERROR USER_VALIDATE_TOKEN_TIME_OUT = new SPARROW_ERROR(SPARROW_MODULE.USER, "16", "user validate token time out");

    public static final SPARROW_ERROR USER_REGISTER_NAME_NULL = new SPARROW_ERROR(SPARROW_MODULE.USER, "17", "user name can't be null");

    public static final SPARROW_ERROR USER_REGISTER_MOBILE_NULL = new SPARROW_ERROR(SPARROW_MODULE.USER, "18", "user mobile can't be null");

    public static final SPARROW_ERROR USER_REGISTER_EMAIL_NULL = new SPARROW_ERROR(SPARROW_MODULE.USER, "19", "user email can't be null");
    public static final SPARROW_ERROR USER_AVATAR_NULL = new SPARROW_ERROR(SPARROW_MODULE.USER, "20", "user avatar can't be null");
    public static final SPARROW_ERROR USER_AVATAR_CUT_COORDINATE_NULL = new SPARROW_ERROR(SPARROW_MODULE.USER, "21", "user avatar cut coordinate can't be null");
    public static final SPARROW_ERROR USER_NOT_LOGIN = new SPARROW_ERROR(SPARROW_MODULE.USER, "22", "user not login");

    //blog
    public static final SPARROW_ERROR BLOG_NOT_THREAD_EDIT_PRIVILEGE = new SPARROW_ERROR(SPARROW_MODULE.BLOG, "01", "No edit privilege");
    public static final SPARROW_ERROR BLOG_THREAD_ID_NOT_EXIST = new SPARROW_ERROR(SPARROW_MODULE.BLOG, "02", "thread id not exist");
    public static final SPARROW_ERROR BLOG_FORUM_CODE_NULL = new SPARROW_ERROR(SPARROW_MODULE.BLOG, "03", "thread forum code is null");
    public static final SPARROW_ERROR BLOG_THREAD_SIMHASH_EXIST = new SPARROW_ERROR(SPARROW_MODULE.BLOG, "04", "thread simhash code has exist");
    public static final SPARROW_ERROR BLOG_THREAD_CRAWLED = new SPARROW_ERROR(SPARROW_MODULE.BLOG, "05", "thread has crawled");
    public static final SPARROW_ERROR BLOG_LOCK = new SPARROW_ERROR(SPARROW_MODULE.BLOG, "06", "thread can't operation");

    //shop
    public static final SPARROW_ERROR SHOP_PRODUCT_NOT_EXIST = new SPARROW_ERROR(SPARROW_MODULE.SHOP, "01", "shop product not exist");
    public static final SPARROW_ERROR SHOP_DATE_NOT_ALLOW = new SPARROW_ERROR(SPARROW_MODULE.SHOP, "02", "shop date not allow");
    public static final SPARROW_ERROR SHOP_DATE_NOT_EXIST = new SPARROW_ERROR(SPARROW_MODULE.SHOP, "03", "shop date not exist");
    public static final SPARROW_ERROR SHOP_ALLOW_NUM_OUT = new SPARROW_ERROR(SPARROW_MODULE.SHOP, "04", "shop allow num out");
    public static final SPARROW_ERROR SHOP_PAY_NOTIFY_ID_ERROR = new SPARROW_ERROR(SPARROW_MODULE.SHOP, "05", "pay notify id error");
    public static final SPARROW_ERROR SHOP_PAY_SIGNATURE_ERROR = new SPARROW_ERROR(SPARROW_MODULE.SHOP, "06", "pay signature error");
    public static final SPARROW_ERROR SHOP_ORDER_NOT_EXIST = new SPARROW_ERROR(SPARROW_MODULE.SHOP, "07", "order not exist");
    public static final SPARROW_ERROR SHOP_ORDER_STATUS_ERROR = new SPARROW_ERROR(SPARROW_MODULE.SHOP, "08", "order status error");
    public static final SPARROW_ERROR SHOP_PAY_STATUS_ERROR = new SPARROW_ERROR(SPARROW_MODULE.SHOP, "09", "pay status error");

    //upload
    public static final SPARROW_ERROR UPLOAD_SERVICE_ERROR = new SPARROW_ERROR(SPARROW_MODULE.UPLOAD, "01", "upload service error");
    public static final SPARROW_ERROR UPLOAD_OUT_OF_SIZE = new SPARROW_ERROR(SPARROW_MODULE.UPLOAD, "02", "upload out of size");
    public static final SPARROW_ERROR UPLOAD_FILE_NAME_NULL = new SPARROW_ERROR(SPARROW_MODULE.UPLOAD, "03", "upload file name null");
    public static final SPARROW_ERROR UPLOAD_FILE_TYPE_ERROR = new SPARROW_ERROR(SPARROW_MODULE.UPLOAD, "04", "upload file type error");
    public static final SPARROW_ERROR UPLOAD_SRC_DESC_PATH_REPEAT = new SPARROW_ERROR(SPARROW_MODULE.UPLOAD, "05", "upload src desc  path repeat");

    //活动
    public static final SPARROW_ERROR ACTIVITY_SCAN_TOKEN_TIME_OUT = new SPARROW_ERROR(SPARROW_MODULE.ACTIVITY, "01", "activity scan token time out");
    public static final SPARROW_ERROR ACTIVITY_TIMES_OUT = new SPARROW_ERROR(SPARROW_MODULE.ACTIVITY, "02", "activity time out");
    public static final SPARROW_ERROR ACTIVITY_RULE_GIFT_TIMES_OUT = new SPARROW_ERROR(SPARROW_MODULE.ACTIVITY, "03", "activity gift time out");

    public SPARROW_ERROR(ModuleSupport moduleSupport, String code, String message) {
        super(false, moduleSupport, code, message);
    }

    public SPARROW_ERROR(boolean system, ModuleSupport moduleSupport, String code, String message) {
        super(system, moduleSupport, code, message);
    }
}
