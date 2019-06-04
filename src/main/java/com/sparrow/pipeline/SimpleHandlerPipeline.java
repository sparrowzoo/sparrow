package com.sparrow.pipeline;

/**
 * @author by harry
 */
public class SimpleHandlerPipeline implements HandlerPipeline {

    private HandlerContext head;
    private HandlerContext tail;

    @Override public void add(Handler handler) {
        HandlerContext handlerContext = new HandlerContext(this,handler);
        if (head == null) {
            head = handlerContext;
            tail = handlerContext;
            return;
        }

        HandlerContext prev = tail.prev==null?head:tail.prev;
        handlerContext.prev = prev;
        prev.next = handlerContext;
        handlerContext.prev = tail;
        tail=handlerContext;
    }

    @Override public void fire(Object arg) {
        head.fire(arg);
    }
}
