package com.sparrow.pipeline;

/**
 * @author by harry
 */
class HandlerContext<T> {
    HandlerPipeline pipeline;
    private String name;
    HandlerContext next;
    HandlerContext prev;
    boolean async;


    public HandlerContext(HandlerPipeline pipeline, Handler handler, boolean async) {
        this.name = handler.getClass().getSimpleName();
        this.pipeline = pipeline;
        this.handler = handler;
        this.async = async;
    }

    public HandlerContext(HandlerPipeline pipeline, Handler handler) {
        this(pipeline, handler, false);
    }

    private Handler handler;

    public void fire(T arg) {
        if (this.async) {
            pipeline.getConsumerThreadPool().submit(new Runnable() {
                @Override
                public void run() {
                    handler.invoke(arg);
                }
            });
        } else {
            handler.invoke(arg);
        }
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
