package com.sparrow.core;

import java.util.Map;

public class ExpirableData<T> {
    private long t;
    private int seconds;
    private Map<String,T> data;

    public ExpirableData(int seconds,Map<String, T> data) {
        this.seconds = seconds;
        this.data = data;
        this.t=System.currentTimeMillis();
    }

    public int getSeconds() {
        return seconds;
    }

    public Map<String,T> getData() {
        return data;
    }

    public boolean isExpire(){
        return (System.currentTimeMillis()-t)/1000>seconds;
    }
}
