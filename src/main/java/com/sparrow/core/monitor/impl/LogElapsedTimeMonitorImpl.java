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
package com.sparrow.core.monitor.impl;

import com.sparrow.core.monitor.ElapsedSection;
import com.sparrow.core.monitor.ElapsedTimeMonitor;
import com.sparrow.utility.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author by harry
 */
public class LogElapsedTimeMonitorImpl implements ElapsedTimeMonitor {
    private Logger logger = LoggerFactory.getLogger(LogElapsedTimeMonitorImpl.class);
    private ThreadLocal<Long> start = new ThreadLocal<Long>();

    @Override public void start() {
        start.set(System.currentTimeMillis());
    }

    @Override public void elapsed(Object... keys) {
        long elapsed = System.currentTimeMillis() - start.get();
        logger.info("{},elapsed {}", StringUtility.join("-", keys), ElapsedSection.section(elapsed));
        start.remove();
    }

    @Override public void elapsedAndRestart(Object... keys) {
        long elapsed = System.currentTimeMillis() - start.get();
        logger.info("{},elapsed {}", StringUtility.join("-", keys), ElapsedSection.section(elapsed));
        start.set(System.currentTimeMillis());
    }

}
