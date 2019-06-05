package com.sparrow.pipeline;

/**
 * @author by harry
 */
public class SimpleHandlerPipeline implements HandlerPipeline {

    public SimpleHandlerPipeline(boolean reverse) {
        this.reverse = reverse;
    }

    public SimpleHandlerPipeline() {
        this.reverse=false;
    }

    private HandlerContext head;
    private HandlerContext tail;

    private boolean reverse;

    @Override public boolean isReverse() {
        return reverse;
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
        if(!reverse) {
            head.fire(arg);
            return;
        }
        tail.fire(arg);
    }
}
