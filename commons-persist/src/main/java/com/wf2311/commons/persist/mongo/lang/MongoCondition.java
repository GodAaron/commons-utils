package com.wf2311.commons.persist.mongo.lang;

import com.wf2311.commons.persist.lang.Condition;
import org.springframework.data.mongodb.core.query.Criteria;

public class MongoCondition implements Condition {

    protected String property;

    protected String operator;

    protected Object value;


    public MongoCondition(String property, String operator, Object value) {
        super();
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    public Criteria generateExpression() {
        if (this.operator.equals(EQUALS)) {
            return new Criteria(this.property).is(this.value);
        } else if (this.operator.equals(LEFT)) {
            return new Criteria(this.property).lt(this.value);
        } else if (this.operator.equals(LEFT_EQ)) {
            return new Criteria(this.property).lte(this.value);
        } else if (this.operator.equals(RIGHT)) {
            return new Criteria(this.property).gt(this.value);
        } else if (this.operator.equals(RIGHT_EQ)) {
            return new Criteria(this.property).gte(this.value);
        } else if (this.operator.equals(NOT_EQUALS)) {
            return new Criteria(this.property).ne(this.value);
        } else if (this.operator.equals(LIKE)) {
            return new Criteria(this.property).regex(".*?" + this.value + ".");
        } else if (this.operator.equals(END)) {
            return new Criteria(this.property).regex("/" + this.value);
        } else if (this.operator.equals(START)) {
            return new Criteria(this.property).regex(this.value + "/");
        } else if (this.operator.equals(BETWEEN)) {
            String[] bts = this.value.toString().split(BETWEEN_SPLIT);
            if (bts.length < 2)
                return null;
            return new Criteria(this.property).lte(bts[0])
                    .andOperator(new Criteria(this.property).gte(bts[1]));
        } else if (this.operator.equals(IN)) {
            if (this.value instanceof Object[])
                return new Criteria(this.property).in((Object[]) value);
        } else if (this.operator.equals(NOT_IN)) {
            if (this.value instanceof Object[])
                return new Criteria(this.property).nin((Object[]) value);
        }

        if (this.operator.equals(NULL))
            return new Criteria(this.property).is(null);
        else if (this.operator.equals(NOT_NULL))
            return new Criteria(this.property).ne(null);
        return null;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String getProperty() {
        return this.property;
    }

    @Override
    public String getOperator() {
        return this.operator;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

}
