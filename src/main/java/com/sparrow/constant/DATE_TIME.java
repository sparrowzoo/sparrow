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

import com.sparrow.constant.magic.DIGIT;
import com.sparrow.enums.DATE_TIME_UNIT;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author harry
 */
public class DATE_TIME {

    /**
     * second
     */
    private static final long SECOND = 1000;
    /**
     * minute
     */
    private static final long MINUTE = SECOND * 60;
    /**
     * hour
     */
    private static final long HOUR = MINUTE * 60;
    /**
     * day
     */
    private static final long DAY = HOUR * 24;
    /**
     * week
     */
    private static final long WEEK = DAY * 7;
    /**
     * month
     */
    private static final long MONTH = DAY * 30;

    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS_MS = "yyyy-MM-dd HH:mm:ss SSS";

    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String FORMAT_MM_DD = "MM-dd";

    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";

    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    private static final Map<String, ThreadLocal<DateFormat>> DATE_FORMAT_CONTAINER = new ConcurrentHashMap<String, ThreadLocal<DateFormat>>();

    private static Lock lock = new ReentrantLock();

    public static DateFormat getInstance(final String format) {
        if (DATE_FORMAT_CONTAINER.get(format) != null) {
            return DATE_FORMAT_CONTAINER.get(format).get();
        } else {
            lock.lock();
            try {
                if (DATE_FORMAT_CONTAINER.get(format) != null) {
                    return DATE_FORMAT_CONTAINER.get(format).get();
                } else {
                    ThreadLocal<DateFormat> dateFormatThreadLocal = new ThreadLocal<DateFormat>() {
                        @Override
                        protected DateFormat initialValue() {
                            return new SimpleDateFormat(format);
                        }
                    };
                    DATE_FORMAT_CONTAINER.put(format, dateFormatThreadLocal);
                    return dateFormatThreadLocal.get();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * min unix timestamp
     */
    public static final Timestamp MIN_UNIX_TIMESTAMP = Timestamp
            .valueOf("1970-01-01 08:00:00");


    public static final Timestamp MAX_UNIX_TIMESTAMP = Timestamp.valueOf("9999-12-31 23:59:59");

    /**
     * time unit from Millis to year
     */
    @SuppressWarnings("serial")
    public static final Map<String, Integer> BEFORE_FORMAT = new LinkedHashMap<String, Integer>() {
        {
            put("秒前", 60);
            put("分钟前", 60);
            put("小时前", 24);
            put("天前", 30);
            put("月前", 12);
            put("年前", 100);
        }
    };

    public static Map<DATE_TIME_UNIT, Integer> DEFAULT_FIRST_VALUE = new HashMap<DATE_TIME_UNIT, Integer>(6);
    public static Map<DATE_TIME_UNIT, Integer> DATE_TIME_UNIT_CALENDER_CONVERTER = new HashMap<DATE_TIME_UNIT, Integer>(7);
    public static Map<DATE_TIME_UNIT, Long> MILLISECOND_UNIT = new HashMap<DATE_TIME_UNIT, Long>(6);

    static {
        //0表示1月
        DEFAULT_FIRST_VALUE.put(DATE_TIME_UNIT.MONTH, DIGIT.ZERO);
        DEFAULT_FIRST_VALUE.put(DATE_TIME_UNIT.DAY, DIGIT.ONE);
        DEFAULT_FIRST_VALUE.put(DATE_TIME_UNIT.HOUR, DIGIT.ZERO);
        DEFAULT_FIRST_VALUE.put(DATE_TIME_UNIT.MINUTE, DIGIT.ZERO);
        DEFAULT_FIRST_VALUE.put(DATE_TIME_UNIT.SECOND, DIGIT.ZERO);
        DEFAULT_FIRST_VALUE.put(DATE_TIME_UNIT.MILLISECOND, DIGIT.ZERO);

        DATE_TIME_UNIT_CALENDER_CONVERTER.put(DATE_TIME_UNIT.YEAR, Calendar.YEAR);
        DATE_TIME_UNIT_CALENDER_CONVERTER.put(DATE_TIME_UNIT.MONTH, Calendar.MONTH);
        DATE_TIME_UNIT_CALENDER_CONVERTER.put(DATE_TIME_UNIT.DAY, Calendar.DATE);
        DATE_TIME_UNIT_CALENDER_CONVERTER.put(DATE_TIME_UNIT.HOUR, Calendar.HOUR_OF_DAY);
        DATE_TIME_UNIT_CALENDER_CONVERTER.put(DATE_TIME_UNIT.MINUTE, Calendar.MINUTE);
        DATE_TIME_UNIT_CALENDER_CONVERTER.put(DATE_TIME_UNIT.SECOND, Calendar.SECOND);
        DATE_TIME_UNIT_CALENDER_CONVERTER.put(DATE_TIME_UNIT.MILLISECOND, Calendar.MILLISECOND);

        /**
         * second
         */
        MILLISECOND_UNIT.put(DATE_TIME_UNIT.SECOND, SECOND);
        /**
         * minute
         */
        MILLISECOND_UNIT.put(DATE_TIME_UNIT.MINUTE, SECOND * 60);
        /**
         * hour
         */
        MILLISECOND_UNIT.put(DATE_TIME_UNIT.HOUR, MINUTE * 60);
        /**
         * day
         */
        MILLISECOND_UNIT.put(DATE_TIME_UNIT.DAY, HOUR * 24);
        /**
         * week
         */
        MILLISECOND_UNIT.put(DATE_TIME_UNIT.WEEK, DAY * 7);
        /**
         * month
         */
        MILLISECOND_UNIT.put(DATE_TIME_UNIT.MONTH, DAY * 30);
    }
}
