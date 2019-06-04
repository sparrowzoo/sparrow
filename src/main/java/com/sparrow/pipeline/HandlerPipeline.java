package com.sparrow.pipeline;

/**
 * @author by harry
 */
public interface HandlerPipeline {

    boolean isAsc();

    void add(Handler handler);

    void fire(Object arg);

}
