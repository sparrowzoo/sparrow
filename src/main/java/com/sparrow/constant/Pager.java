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

/**
 * 分页控件配置
 *
 * @author harry
 * @version 1.0
 */
public class Pager {
    /**
     * 分页控件的码页
     */
    public static final String PAGE_INDEX = "$pageIndex";
    /**
     * FORM表单提交的action的格式
     */
    public static final String ACTION_PAGE_FORMAT ="javascript:$.page.submit($pageIndex);";
}
