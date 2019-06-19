package com.sparrow.core.cache;

public class ExpirableData<T> {
    private long t;
    private int seconds;
    private T data;

    public ExpirableData(int seconds,T data) {
        this.seconds = seconds;
        this.data = data;
        this.t=System.currentTimeMillis();
    }

    public int getSeconds() {
        return seconds;
    }

    public T getData() {
        return data;
    }

    public boolean isExpire(){
        return (System.currentTimeMillis()-t)/1000>seconds;
    }
}
