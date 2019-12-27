package com.sparrow.tracer;

import java.util.List;

public interface Span {
    Tracer getTracer();

    void finish();

    String getName();

    Integer getId();

    String span();

    Span parent();

    Span follower();

    List<Span> children();
}
