package com.sparrow.tracer;

public interface Tracer {
    String getId();

    SpanBuilder getSpanBuilder();

    SpanBuilder build(String spanName);

    Span root();

    Span parentCursor();

    String walking();
}
