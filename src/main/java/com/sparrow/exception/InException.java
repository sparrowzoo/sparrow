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

package com.sparrow.exception;

import com.sparrow.protocol.ErrorSupport;
import com.sparrow.utility.StringUtility;

import java.util.Arrays;
import java.util.List;

/**
 * @author harry
 */
public class InException extends Exception {
    private Integer code;
    private String key;
    private List<Object> parameters;

    public InException(ErrorSupport errorSupport, String suffix, List<Object> parameters) {
        super(errorSupport.getMessage());
        this.key = errorSupport.name() + "_" + suffix;
        this.code = errorSupport.getCode();
        if (parameters != null && parameters.size() > 0 && !StringUtility.isNullOrEmpty(parameters.get(0))) {
            this.parameters = parameters;
        }
    }

    public InException(ErrorSupport errorSupport, String suffix, Object parameter) {
        this(errorSupport, suffix, Arrays.asList(parameter));
    }

    public InException(ErrorSupport errorSupport, List<Object> parameters) {
        this(errorSupport, null, parameters);
    }

    public InException(ErrorSupport errorSupport, String suffix) {
        this(errorSupport, suffix, "");
    }

    public InException(ErrorSupport errorSupport) {
        this(errorSupport, null, "");
    }

    public Integer getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }
}
