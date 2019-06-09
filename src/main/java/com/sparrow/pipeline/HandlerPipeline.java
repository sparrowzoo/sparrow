package com.sparrow.pipeline;

/**
 * @author by harry
 */
public interface HandlerPipeline {

    boolean isReverse();

    void add(Handler handler);

    void fire(Object arg);

}
