package com.sparrow.tracer;

import com.sparrow.tracer.impl.TracerImpl;

import java.util.UUID;

public class TracerBuilder {
    public static Tracer startTracer(String traceId) {
        return new TracerImpl(traceId);
    }

    public static Tracer startTracer() {
        return new TracerImpl(UUID.randomUUID().toString());
    }
}
