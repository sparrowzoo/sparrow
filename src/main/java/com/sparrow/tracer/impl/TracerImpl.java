package com.sparrow.tracer.impl;

import com.sparrow.tracer.Span;
import com.sparrow.tracer.SpanBuilder;
import com.sparrow.tracer.Tracer;
import com.sparrow.utility.CollectionsUtility;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TracerImpl implements Tracer {
    /**
     * span id 生成器
     */
    private AtomicInteger nextId = new AtomicInteger(0);
    /**
     * trace id
     */
    private String traceId;
    /**
     * 根span
     */
    private Span root;
    /**
     * parent 当前span指针
     */
    private ThreadLocal<Span> cursor = new ThreadLocal<>();
    /**
     * 全局span builder 对象
     */
    private SpanBuilder spanBuilder;

    public Integer nextId() {
        return nextId.incrementAndGet();
    }

    public TracerImpl(String traceId) {
        this.traceId = traceId;
    }

    public TracerImpl(String traceId, int startId) {
        this.traceId = traceId;
        this.nextId = new AtomicInteger(startId);
    }

    public void setRoot(Span root) {
        this.root = root;
    }

    public Span root() {
        return this.root;
    }

    public void setCursor(Span current) {
        this.cursor.set(current);
    }

    @Override
    public Span cursor() {
        return this.cursor.get();
    }

    @Override
    public String getId() {
        return this.traceId;
    }

    @Override
    public SpanBuilder getSpanBuilder() {
        return this.spanBuilder;
    }

    @Override
    public SpanBuilder build(String spanName) {
        SpanBuilderImpl builder = new SpanBuilderImpl(this);
        builder.name(spanName);
        this.spanBuilder = builder;
        return builder;
    }

    private void recursion(Span span, Map<Span, Integer> container, int depth) {
        if (span.follower() == null && CollectionsUtility.isNullOrEmpty(span.children())) {
            return;
        }
        if (!CollectionsUtility.isNullOrEmpty(span.children())) {
            for (Span child : span.children()) {
                container.put(child, depth + 1);
                recursion(child, container, depth + 1);
            }
        }
        if (span.follower() != null) {
            container.put(span.follower(), depth);
            recursion(span.follower(), container, depth);
        }
    }

    private String generate(int depth) {
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            prefix.append("--");
        }
        return prefix.toString();
    }

    private String spanToMarkdown(SpanImpl span) {
        return span.getId() + "|"
                + (span.getParent() == null ? -1 : span.getParent().getId()) + "|"
                + (span.isFollower() ? "F" : "C") + "|"
                + span.getName() + "|"
                + span.getStartTime() + "|"
                + ((span.getEndTime() == null ? System.currentTimeMillis() : span.getEndTime()) - span.getStartTime()) + "ms";
    }

    @Override
    public String walking() {
        Map<Span, Integer> spanDepthContainer = new LinkedHashMap<>();
        spanDepthContainer.put(root, 0);
        this.recursion(root, spanDepthContainer, 0);
        StringBuilder walking = new StringBuilder("traceId:" + this.traceId);
        walking.append("###depth|span-id|parent-id|F/C|span-name | start|duration");
        walking.append("###---|---|---|---|---|---|---");
        for (Span span : spanDepthContainer.keySet()) {
            walking.append("###");
            walking.append(spanDepthContainer.get(span));
            walking.append("|");
            walking.append(this.spanToMarkdown((SpanImpl) span));
        }
        return walking.toString();
    }

    @Override
    public void log(Logger logger, String parameters, String executeContext) {
        logger.info("tracer id:{} parameter:{} execute logs {} execute duration {}",
                this.getId(),
                parameters,
                executeContext,
                this.walking());
    }
}
