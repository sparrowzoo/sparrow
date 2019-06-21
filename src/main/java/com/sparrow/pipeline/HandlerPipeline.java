package com.sparrow.pipeline;

import java.util.concurrent.ExecutorService;

/**
 * @author by harry
 */
public interface HandlerPipeline {

    boolean isReverse();

    void add(Handler handler);

    void addAsyc(Handler handler);

    void fire(Object arg);

    ExecutorService getConsumerThreadPool();
}
