package com.sparrow.pipeline;

import com.sparrow.concurrent.SparrowThreadFactory;

import java.util.concurrent.*;

/**
 * @author by harry
 */
public class SimpleHandlerPipeline implements HandlerPipeline {
    private ExecutorService consumerThreadPool = Executors.newCachedThreadPool(new SparrowThreadFactory.Builder().namingPattern("pipeline-async-%d").build());

    public SimpleHandlerPipeline(boolean reverse) {
        this.reverse = reverse;
    }

    public SimpleHandlerPipeline() {
        this.reverse = false;
    }

    private HandlerContext head;
    private HandlerContext tail;

    private boolean reverse;

    @Override
    public boolean isReverse() {
        return reverse;
    }


    @Override
    public void add(Handler handler) {
        this.add(handler, false);
    }

    private void add(Handler handler, boolean asyc) {
        HandlerContext handlerContext = new HandlerContext(this, handler, asyc);
        if (head == null) {
            head = handlerContext;
            tail = handlerContext;
            return;
        }

        handlerContext.prev = tail;
        tail.next = handlerContext;
        tail = handlerContext;
    }

    @Override
    public void addAsyc(Handler handler) {
        this.add(handler, true);
    }

    @Override
    public void fire(Object arg) {
        if (!reverse) {
            head.fire(arg);
            return;
        }
        tail.fire(arg);
    }

    public ExecutorService getConsumerThreadPool() {
        return consumerThreadPool;
    }
}
