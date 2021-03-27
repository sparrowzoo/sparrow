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

import com.sparrow.enums.Order;

import java.util.Comparator;
import java.util.Map;

/**
 * @author harry
 */
public class MapValueComparator<T> implements Comparator<T> {
    Map<T, Double> map;

    Order order;

    public MapValueComparator(Map<T, Double> map, Order order) {
        this.map = map;
        order = order;
    }

    public MapValueComparator(Map<T, Double> map) {
        this.map = map;
        order = Order.DESC;
    }

    @Override
    public int compare(T a, T b) {
        if (map.get(a) >= map.get(b)) {
            return order == Order.DESC ? -1 : 1;
        }
        return order == Order.DESC ? 1 : -1;
    }
}
