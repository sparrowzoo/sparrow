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

import com.sparrow.enums.DIMENSION_LEVEL;
import com.sparrow.protocol.BusinessException;

import java.util.Map;

/**
 * @author harry
 */
public interface PrivilegeSupport {

    boolean accessible(Long userId,
        String resource, String value) throws BusinessException;

    String getStrategy(Long userId,
        String strategyCode) throws BusinessException;

    void setPrivilege(String selectedPolicy, String selectedResource,
        Long groupId) throws BusinessException;

    /**
     * 数据权限过滤接口
     *
     * @param filter key 为需要过滤的字段，value为需要过滤的级别
     * @return
     */
    String getFilter(Map<String, DIMENSION_LEVEL> filter);
}
