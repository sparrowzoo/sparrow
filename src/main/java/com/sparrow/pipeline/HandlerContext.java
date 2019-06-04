package com.sparrow.pipeline;

/**
 * @author by harry
 */
 class HandlerContext {
     HandlerPipeline pipeline;
     volatile HandlerContext next;
     volatile HandlerContext prev;

    public HandlerContext(HandlerPipeline pipeline,Handler handler) {
        this.pipeline=pipeline;
        this.handler = handler;
    }

    private Handler handler;

    public void fire(Object arg){
        handler.invoke(arg);
        if(next!=null){
            next.handler.invoke(arg);
        }
    }
}
