package com.sparrow.tracer;

public interface TracerAccessor {
    Integer getAlarmTimeout();

    Tracer getTracer();

    Span getSpan();

    void setSpan(Span localSpan);
}
