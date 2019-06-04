package com.sparrow.pipeline;

/**
 * @author by harry
 */
public class SimpleHandlerPipeline implements HandlerPipeline {

    public SimpleHandlerPipeline(boolean asc) {
        this.asc = asc;
    }

    private HandlerContext head;
    private HandlerContext tail;

    private boolean asc;

    @Override public boolean isAsc() {
        return asc;
    }


    @Override public void add(Handler handler) {
        HandlerContext handlerContext = new HandlerContext(this,handler);
        if (head == null) {
            head = handlerContext;
            tail = handlerContext;
            return;
        }

        handlerContext.prev = tail;
        tail.next = handlerContext;
        tail=handlerContext;
    }

    @Override public void fire(Object arg) {
        if(asc) {
            head.fire(arg);
            return;
        }
        tail.fire(arg);
    }
}
