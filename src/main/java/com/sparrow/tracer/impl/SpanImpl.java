package com.sparrow.tracer.impl;


import com.sparrow.tracer.Span;
import com.sparrow.tracer.Tracer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpanImpl implements Span {
    public SpanImpl(Tracer tracer, Long startTime, String name) {
        this.tracer = (TracerImpl) tracer;
        this.startTime = startTime;
        this.name = name;
    }

    private TracerImpl tracer;
    private Span parent;
    private List<Span> children;
    private Span follower;
    private boolean finished;
    private boolean isFollower;
    private boolean forwardParentCursor;
    private Long startTime;
    private String name;
    private Integer id;
    private Long endTime;


    public void setFollower(Span follower) {
        this.follower = follower;
    }

    public void setParent(Span parent) {
        this.parent = parent;
    }

    public void addChild(Span child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }


    public boolean isFinished() {
        return this.finished;
    }

    public void setFollower(boolean follower) {
        isFollower = follower;
    }

    @Override
    public void finish() {
        this.endTime = System.currentTimeMillis();
        this.finished = true;

        if (this.parent == null) {
            return;
        }
        //如果未向前移动，则指针不回退
        if (!this.forwardParentCursor) {
            return;
        }
        if (this.parent.children() == null || this.parent.children().size() == 0) {
            return;
        }
        if (this.tracer.parentCursor() == null) {
            return;
        }
        if (this.tracer.parentCursor().parent() == null) {
            return;
        }
        this.tracer.setParentCursor(this.tracer.parentCursor().parent());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String span() {
        return this.id + "|"
                + (this.parent == null ? -1 : this.parent.getId()) + "|"
                + (this.isFollower ? "F" : "C") + "|"
                + this.name + "|"
                + this.startTime + "|"
                + (this.endTime - this.startTime) + "ms";
    }

    @Override
    public Span parent() {
        return this.parent;
    }

    @Override
    public Span follower() {
        return this.follower;
    }

    @Override
    public List<Span> children() {
        return this.children;
    }

    public Tracer getTracer() {
        return tracer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpanImpl span = (SpanImpl) o;
        return Objects.equals(startTime, span.startTime) &&
                Objects.equals(name, span.name) &&
                Objects.equals(endTime, span.endTime) &&
                Objects.equals(children, span.children) &&
                Objects.equals(follower, span.follower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, name, endTime, children, follower);
    }

    public void setForwardParentCursor(boolean forwardParentCursor) {
        this.forwardParentCursor = forwardParentCursor;
    }
}
