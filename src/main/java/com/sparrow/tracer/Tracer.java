package com.sparrow.tracer;

import org.slf4j.Logger;

public interface Tracer {
    String getId();

    SpanBuilder getSpanBuilder();

    SpanBuilder build(String spanName);

    Span root();

    Span cursor();

    String walking();

    void log(Logger logger, String parameters, String executeContext);
}
