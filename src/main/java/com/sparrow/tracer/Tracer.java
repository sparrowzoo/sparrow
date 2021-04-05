package com.sparrow.tracer;

import org.slf4j.Logger;

public interface Tracer {

    boolean isTimeout();

    String getId();

    SpanBuilder spanBuilder();

    Span root();

    Span cursor();

    String walking();

    void log(Logger logger, String parameters);
}
