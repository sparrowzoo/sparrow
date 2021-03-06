package com.sparrow.support.db;

import com.sparrow.orm.query.AGGREGATE;

public class AggregateCriteria {
    private AGGREGATE aggregate;
    private String field;

    public AggregateCriteria(AGGREGATE aggregate, String field) {
        this.aggregate = aggregate;
        this.field = field;
    }

    public AGGREGATE getAggregate() {
        return aggregate;
    }

    public void setAggregate(AGGREGATE aggregate) {
        this.aggregate = aggregate;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
