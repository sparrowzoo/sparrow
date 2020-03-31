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

import com.sparrow.support.ERROR;

/**
 * first byte:
 * <p>
 * 1 表示系统模块
 * <p>
 * 2 模块错误
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
public class SPARROW_ERROR {

    public static final ERROR SYSTEM_SERVER_ERROR = ERROR.system(SPARROW_MODULE.GLOBAL, "01", "System error");

    public static final ERROR SYSTEM_SERVICE_UNAVAILABLE = ERROR.system(SPARROW_MODULE.GLOBAL, "02", "Service unavailable");

    public static final ERROR SYSTEM_REMOTE_SERVICE_UNAVAILABLE = ERROR.system(SPARROW_MODULE.GLOBAL, "03", "Remote Service unavailable");

    public static final ERROR SYSTEM_PERMISSION_DENIED = ERROR.system(SPARROW_MODULE.GLOBAL, "04", "Permission denied");

    public static final ERROR SYSTEM_ILLEGAL_REQUEST = ERROR.system(SPARROW_MODULE.GLOBAL, "05", "Illegal request");

    public static final ERROR GLOBAL_DB_ADD_ERROR = ERROR.system(SPARROW_MODULE.GLOBAL, "06", "add error");

    public static final ERROR GLOBAL_DB_DELETE_ERROR = ERROR.system(SPARROW_MODULE.GLOBAL, "07", "delete error");

    public static final ERROR GLOBAL_DB_UPDATE_ERROR = ERROR.system(SPARROW_MODULE.GLOBAL, "08", "update error");

    public static final ERROR GLOBAL_DB_LOAD_ERROR = ERROR.system(SPARROW_MODULE.GLOBAL, "09", "load error");


    public static final ERROR GLOBAL_REQUEST_ID_NOT_EXIST = ERROR.system(SPARROW_MODULE.GLOBAL, "11", "Request id not exist");

    public static final ERROR GLOBAL_VALIDATE_CODE_ERROR = ERROR.system(SPARROW_MODULE.GLOBAL, "12", "ValidateCode error");

    public static final ERROR GLOBAL_CONTENT_IS_NULL = ERROR.system(SPARROW_MODULE.GLOBAL, "13", "Content is null");

    public static final ERROR GLOBAL_CONTAIN_ILLEGAL_WEBSITE = ERROR.system(SPARROW_MODULE.GLOBAL, "14", "Contain illegal website");

    public static final ERROR GLOBAL_CONTAIN_ADVERTISING = ERROR.system(SPARROW_MODULE.GLOBAL, "15", "Contain advertising");

    public static final ERROR GLOBAL_CONTENT_IS_ILLEGAL = ERROR.system(SPARROW_MODULE.GLOBAL, "16", "Content is illegal");

    public static final ERROR GLOBAL_CONTENT_DUPLICATE = ERROR.system(SPARROW_MODULE.GLOBAL, "17", "Content duplicate");

    public static final ERROR GLOBAL_UNSUPPORTED_IMAGE_TYPE = ERROR.system(SPARROW_MODULE.GLOBAL, "18",
            "Unsupported image type only support JPG, GIF, PNG");

    public static final ERROR GLOBAL_IMAGE_SIZE_TOO_LARGE = ERROR.system(SPARROW_MODULE.GLOBAL, "19", "Image size too large");

    public static final ERROR GLOBAL_ACCOUNT_ILLEGAL = ERROR.system(SPARROW_MODULE.GLOBAL, "20",
            "Account or ip or app is illegal, can not continue");

    public static final ERROR GLOBAL_OUT_OF_TIMES_LIMIT = ERROR.system(SPARROW_MODULE.GLOBAL, "21", "Out of times limit");

    public static final ERROR GLOBAL_ADMIN_CAN_NOT_OPERATION = ERROR.system(SPARROW_MODULE.GLOBAL, "22", "Admin can't operation");

    public static final ERROR GLOBAL_PARAMETER_NULL = ERROR.system(SPARROW_MODULE.GLOBAL, "23", "Parameter is null");

    public static final ERROR GLOBAL_REQUEST_REPEAT = ERROR.system(SPARROW_MODULE.GLOBAL, "24", "Request repeat");

    public static final ERROR GLOBAL_EMAIL_SEND_FAIL = ERROR.system(SPARROW_MODULE.GLOBAL, "25", "email send fail");

    public static final ERROR GLOBAL_OPERATION_VALIDATE_STATUS_INVALID = ERROR.system(SPARROW_MODULE.GLOBAL, "26", "operation validate status is invalid");

    public static final ERROR GLOBAL_OPERATION_VALIDATE_ROLE_INVALID = ERROR.system(SPARROW_MODULE.GLOBAL, "27", "operation validate role is invalid");

    public static final ERROR GLOBAL_PARAMETER_IS_ILLEGAL = ERROR.system(SPARROW_MODULE.GLOBAL, "28", "parameter is illegal");

    public static final ERROR GLOBAL_SMS_SEND_ERROR = ERROR.system(SPARROW_MODULE.GLOBAL, "29", "short message service error");


    public static final ERROR USER_NAME_EXIST = ERROR.business(SPARROW_MODULE.USER, "01", "User name exist");

    public static final ERROR USER_EMAIL_EXIST = ERROR.business(SPARROW_MODULE.USER, "02", "User email exist");

    public static final ERROR USER_MOBILE_EXIST = ERROR.business(SPARROW_MODULE.USER, "03", "User mobile exist");

    public static final ERROR USER_OLD_PASSWORD_ERROR = ERROR.business(SPARROW_MODULE.USER, "04", "User old password error");

    public static final ERROR USER_NAME_NOT_EXIST = ERROR.business(SPARROW_MODULE.USER, "05", "Username not exist");

    public static final ERROR USER_MOBILE_NOT_EXIST = ERROR.business(SPARROW_MODULE.USER, "06", "Mobile not exist");

    public static final ERROR USER_EMAIL_NOT_EXIST = ERROR.business(SPARROW_MODULE.USER, "07", "User email not exist");

    public static final ERROR USER_PASSWORD_ERROR = ERROR.business(SPARROW_MODULE.USER, "08", "User password error");

    public static final ERROR USER_PASSWORD_FORMAT_ERROR = ERROR.business(SPARROW_MODULE.USER, "09", "User password format error");

    public static final ERROR USER_DISABLED = ERROR.business(SPARROW_MODULE.USER, "10", "User disabled");

    public static final ERROR USER_NOT_ACTIVATE = ERROR.business(SPARROW_MODULE.USER, "11", "User not activate");

    public static final ERROR USER_PASSWORD_VALIDATE_TOKEN_ERROR = ERROR.business(SPARROW_MODULE.USER, "12", "user password validate_token error");

    public static final ERROR USER_VALIDATE_TIME_OUT = ERROR.business(SPARROW_MODULE.USER, "13", "user validate_code time out");

    public static final ERROR USER_VALIDATE_NOT_EXIST = ERROR.business(SPARROW_MODULE.USER, "14", "user validate_code not exist");

    public static final ERROR USER_VALIDATE_VALID = ERROR.business(SPARROW_MODULE.USER, "15", "user validate code valid");

    public static final ERROR USER_VALIDATE_TOKEN_TIME_OUT = ERROR.business(SPARROW_MODULE.USER, "16", "user validate token time out");

    public static final ERROR USER_REGISTER_NAME_NULL = ERROR.business(SPARROW_MODULE.USER, "17", "user name can't be null");

    public static final ERROR USER_REGISTER_MOBILE_NULL = ERROR.business(SPARROW_MODULE.USER, "18", "user mobile can't be null");

    public static final ERROR USER_REGISTER_EMAIL_NULL = ERROR.business(SPARROW_MODULE.USER, "19", "user email can't be null");
    public static final ERROR USER_AVATAR_NULL = ERROR.business(SPARROW_MODULE.USER, "20", "user avatar can't be null");
    public static final ERROR USER_AVATAR_CUT_COORDINATE_NULL = ERROR.business(SPARROW_MODULE.USER, "21", "user avatar cut coordinate can't be null");
    public static final ERROR USER_NOT_LOGIN = ERROR.business(SPARROW_MODULE.USER, "22", "user not login");

    //blog
    public static final ERROR BLOG_NOT_THREAD_EDIT_PRIVILEGE = ERROR.business(SPARROW_MODULE.BLOG, "01", "No edit privilege");
    public static final ERROR BLOG_THREAD_ID_NOT_EXIST = ERROR.business(SPARROW_MODULE.BLOG, "02", "thread id not exist");
    public static final ERROR BLOG_FORUM_CODE_NULL = ERROR.business(SPARROW_MODULE.BLOG, "03", "thread forum code is null");
    public static final ERROR BLOG_THREAD_SIMHASH_EXIST = ERROR.business(SPARROW_MODULE.BLOG, "04", "thread simhash code has exist");
    public static final ERROR BLOG_THREAD_CRAWLED = ERROR.business(SPARROW_MODULE.BLOG, "05", "thread has crawled");
    public static final ERROR BLOG_LOCK = ERROR.business(SPARROW_MODULE.BLOG, "06", "thread can't operation");

    //shop
    public static final ERROR SHOP_PRODUCT_NOT_EXIST = ERROR.business(SPARROW_MODULE.SHOP, "01", "shop product not exist");
    public static final ERROR SHOP_DATE_NOT_ALLOW = ERROR.business(SPARROW_MODULE.SHOP, "02", "shop date not allow");
    public static final ERROR SHOP_DATE_NOT_EXIST = ERROR.business(SPARROW_MODULE.SHOP, "03", "shop date not exist");
    public static final ERROR SHOP_ALLOW_NUM_OUT = ERROR.business(SPARROW_MODULE.SHOP, "04", "shop allow num out");
    public static final ERROR SHOP_PAY_NOTIFY_ID_ERROR = ERROR.business(SPARROW_MODULE.SHOP, "05", "pay notify id error");
    public static final ERROR SHOP_PAY_SIGNATURE_ERROR = ERROR.business(SPARROW_MODULE.SHOP, "06", "pay signature error");
    public static final ERROR SHOP_ORDER_NOT_EXIST = ERROR.business(SPARROW_MODULE.SHOP, "07", "order not exist");
    public static final ERROR SHOP_ORDER_STATUS_ERROR = ERROR.business(SPARROW_MODULE.SHOP, "08", "order status error");
    public static final ERROR SHOP_PAY_STATUS_ERROR = ERROR.business(SPARROW_MODULE.SHOP, "09", "pay status error");

    //upload
    public static final ERROR UPLOAD_SERVICE_ERROR = ERROR.business(SPARROW_MODULE.UPLOAD, "01", "upload service error");
    public static final ERROR UPLOAD_OUT_OF_SIZE = ERROR.business(SPARROW_MODULE.UPLOAD, "02", "upload out of size");
    public static final ERROR UPLOAD_FILE_NAME_NULL = ERROR.business(SPARROW_MODULE.UPLOAD, "03", "upload file name null");
    public static final ERROR UPLOAD_FILE_TYPE_ERROR = ERROR.business(SPARROW_MODULE.UPLOAD, "04", "upload file type error");
    public static final ERROR UPLOAD_SRC_DESC_PATH_REPEAT = ERROR.business(SPARROW_MODULE.UPLOAD, "05", "upload src desc  path repeat");

    //活动
    public static final ERROR ACTIVITY_SCAN_TOKEN_TIME_OUT = ERROR.business(SPARROW_MODULE.ACTIVITY, "01", "activity scan token time out");
    public static final ERROR ACTIVITY_TIMES_OUT = ERROR.business(SPARROW_MODULE.ACTIVITY, "02", "activity time out");
    public static final ERROR ACTIVITY_RULE_GIFT_TIMES_OUT = ERROR.business(SPARROW_MODULE.ACTIVITY, "03", "activity gift time out");
}
