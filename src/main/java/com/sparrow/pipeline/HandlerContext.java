package com.sparrow.pipeline;

/**
 * @author by harry
 */
class HandlerContext<T> {
    HandlerPipeline pipeline;
    private String name;
    volatile HandlerContext next;
    volatile HandlerContext prev;

    public HandlerContext(HandlerPipeline pipeline, Handler handler) {
        this.name = handler.getClass().getSimpleName();
        this.pipeline = pipeline;
        this.handler = handler;
    }

    private Handler handler;

    public void fire(T arg) {
        handler.invoke(arg);

        if (!pipeline.isReverse()) {
            if (next != null) {
                next.fire(arg);
            }
            return;
        }

        if (prev != null) {
            prev.fire(arg);
        }
    }
}
