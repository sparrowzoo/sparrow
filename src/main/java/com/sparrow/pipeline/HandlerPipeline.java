package com.sparrow.pipeline;

/**
 * @author by harry
 */
public interface HandlerPipeline {

    void add(Handler handler);

    void fire(Object arg);

}
