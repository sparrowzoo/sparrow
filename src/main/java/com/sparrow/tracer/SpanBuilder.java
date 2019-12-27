package com.sparrow.tracer;

public interface SpanBuilder {
    /**
     * 在父span 中执行
     *
     * @param parent
     * @return
     */
    SpanBuilder asChild(String parent);

    /**
     * 在父span 中执行
     *
     * @param parent
     * @return
     */
    SpanBuilder asChild(Span parent);

    SpanBuilder asChild();

    SpanBuilder name(String operationName);

    /**
     * 考虑异步多线程时，存在并发问题，这里需要手动设置
     *
     * @param forwardParentCursor
     * @return
     */
    SpanBuilder forwardParentCursor(boolean forwardParentCursor);

    Span start();
}