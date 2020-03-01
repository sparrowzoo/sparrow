package com.sparrow.tracer.impl;

import com.sparrow.protocol.POJO;
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
        this.tracer.setCursor(this);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Span setTag(String key, String value) {
        return null;
    }

    @Override
    public Span setTag(String key, boolean value) {
        return null;
    }

    @Override
    public Span setTag(String key, Number value) {
        return null;
    }

    @Override
    public Span setTag(String key, POJO t) {
        return null;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int duration() {
        return (int) (this.getEndTime() - this.getStartTime());
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

    public Span getParent() {
        return parent;
    }

    public List<Span> getChildren() {
        return children;
    }

    public Span getFollower() {
        return follower;
    }

    public boolean isFollower() {
        return isFollower;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Span{" +
                "tracer=" + tracer +
                ", parent=" + parent +
                ", children=" + children +
                ", follower=" + follower +
                ", finished=" + finished +
                ", isFollower=" + isFollower +
                ", startTime=" + startTime +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, name, endTime, children, follower);
    }
}
