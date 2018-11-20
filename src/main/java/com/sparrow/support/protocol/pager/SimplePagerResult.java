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

package com.sparrow.support.protocol.pager;

import java.util.List;

/**
 * simple pager result without html
 * @author harry
 */
public class SimplePagerResult<T> extends SimplePager{

    public SimplePagerResult(Integer currentPageIndex, Integer pageSize) {
        super(currentPageIndex, pageSize);
    }

    protected Long recordCount;

    protected List<T> list;

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 根据总记录数和每页记录数获取最后页码
     *
     * @return
     */
    public Integer getLastPageIndex() {
        return (int) Math.ceil((double) this.recordCount / this.pageSize);
    }

    public boolean more() {
        return getCurrentPageIndex() < getLastPageIndex();
    }
}
