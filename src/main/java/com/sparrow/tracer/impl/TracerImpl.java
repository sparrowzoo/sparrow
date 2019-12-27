package com.sparrow.tracer.impl;

import com.sparrow.tracer.Span;
import com.sparrow.tracer.SpanBuilder;
import com.sparrow.tracer.Tracer;
import com.sparrow.utility.CollectionsUtility;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TracerImpl implements Tracer {
    private AtomicInteger nextId = new AtomicInteger(0);
    private String traceId;
    private Span root;
    private Span parentCursor;
    private SpanBuilder spanBuilder;
    private Map<String, Span> spanMap = new HashMap<>();

    public Map<String, Span> getSpanMap() {
        return spanMap;
    }

    public Integer nextId(){
       return nextId.incrementAndGet();
    }

    public void putSpan(String key, Span span) {
        this.spanMap.put(key, span);
    }

    public TracerImpl(String traceId) {
        this.traceId = traceId;
    }

    public void setRoot(Span root) {
        this.root = root;
    }

    public Span root() {
        return this.root;
    }

    public void setParentCursor(Span current) {
        this.parentCursor=current;
    }

    @Override
    public Span parentCursor() {
        return this.parentCursor;
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

    @Override
    public String walking() {
        Map<Span, Integer> spanDepthContainer = new LinkedHashMap<>();
        spanDepthContainer.put(root, 0);
        this.recursion(root, spanDepthContainer, 0);
        StringBuilder walking = new StringBuilder("traceId:" + this.traceId);
        walking.append("\ndepth|span-id|parent-id|f/c|span-name | start|duration");
        walking.append("\n---|---|---|---|---|---|---");
        for (Span span : spanDepthContainer.keySet()) {
            walking.append("\n");
            walking.append(spanDepthContainer.get(span));
            walking.append("|");
            walking.append(span.span());
        }
        return walking.toString();
    }
}
