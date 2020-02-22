package com.sparrow.tracer;

import com.sparrow.protocol.POJO;

import java.util.List;

public interface Span {
    Tracer getTracer();

    void finish();

    String getName();

    /**
     * Set a key:value tag on the Span.
     */
    Span setTag(String key, String value);

    /**
     * Same as {@link #setTag(String, String)}, but for boolean values.
     */
    Span setTag(String key, boolean value);

    /**
     * Same as {@link #setTag(String, String)}, but for numeric values.
     */
    Span setTag(String key, Number value);

    Span setTag(String key, POJO t);

    Integer getId();

    Span parent();

    Span follower();

    List<Span> children();

    int duration();
}
