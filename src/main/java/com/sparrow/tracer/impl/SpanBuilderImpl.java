package com.sparrow.tracer.impl;

import com.sparrow.tracer.Span;
import com.sparrow.tracer.SpanBuilder;
import com.sparrow.tracer.Tracer;

public class SpanBuilderImpl implements SpanBuilder {
    public SpanBuilderImpl() {
    }

    public SpanBuilderImpl(Tracer tracer) {
        this.tracer = (TracerImpl) tracer;
    }

    private String name;
    private SpanImpl parent;
    private TracerImpl tracer;
    /**
     * 进入span时，是否将指针移动,默认移
     */
    private boolean forwardParentCursor = true;

    @Override
    public SpanBuilder asChild(String parent) {
        return asChild(tracer.getSpanMap().get(parent));
    }

    @Override
    public SpanBuilder asChild(Span parent) {
        SpanBuilderImpl spanBuilder = new SpanBuilderImpl();
        spanBuilder.parent = (SpanImpl) parent;
        if (spanBuilder.parent == null) {
            spanBuilder.parent = (SpanImpl) tracer.parentCursor();
        }
        if (spanBuilder.parent == null) {
            spanBuilder.parent = (SpanImpl) tracer.root();
        }
        spanBuilder.tracer = (TracerImpl) parent.getTracer();
        return spanBuilder;
    }

    @Override
    public SpanBuilder asChild() {
        return asChild(tracer.parentCursor());
    }

    @Override
    public SpanBuilder name(String operationName) {
        this.name = operationName;
        return this;
    }

    public SpanBuilder forwardParentCursor(boolean forwardParentCursor) {
        this.forwardParentCursor = forwardParentCursor;
        return this;
    }


    @Override
    public Span start() {
        SpanImpl span = new SpanImpl(this.tracer, System.currentTimeMillis(), this.name);
        span.setForwardParentCursor(forwardParentCursor);
        span.setId(this.tracer.nextId());
        tracer.putSpan(this.name, span);
        if (this.tracer.root() == null) {
            this.tracer.setRoot(span);
            this.tracer.setParentCursor(span);
        }

        if (forwardParentCursor) {
            tracer.setParentCursor(span);
        }
        if (this.parent == null) {
            return span;
        }
        /**
         *See http://opentracing.io/spec/#causal-span-references for more information about CHILD_OF references
         *public static final String CHILD_OF = "child_of";
         * See http://opentracing.io/spec/#causal-span-references for more information about FOLLOWS_FROM references
         public static final String FOLLOWS_FROM = "follows_from";
         **/
        if (this.parent.isFinished()) {
            this.parent.setFollower(span);
            span.setFollower(true);
        } else {
            this.parent.addChild(span);
            span.setFollower(false);
        }
        span.setParent(this.parent);
        return span;
    }
}
