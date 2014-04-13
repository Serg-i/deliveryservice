package com.itechart.deliveryservice.utils;

import java.util.*;

public class SearchParams {

    List<Pair> mp = new ArrayList<Pair>();

    public enum Operator {
        EQUAL,
        LESS,
        GREATER
    }

    public class Pair {
        private String key;
        private OpAndValue value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public OpAndValue getValue() {
            return value;
        }

        public void setValue(OpAndValue value) {
            this.value = value;
        }

        public Pair(String key, OpAndValue value) {
            this.key = key;
            this.value = value;
        }
    }

    public class OpAndValue {
        public Operator operator;
        public Object value;
        public OpAndValue(Operator op, Object value) {
            this.operator = op;
            this.value = value;
        }
    }

    public void addParam(String methodName, Object value) {

        OpAndValue ov = new OpAndValue(Operator.EQUAL, value);
        mp.add(new Pair(methodName, ov));
    }

    public void addParam(String methodName, Operator op, Date value) {

        OpAndValue ov = new OpAndValue(op, value);
        mp.add(new Pair(methodName, ov));
    }

    public Iterator<Pair> iterator() {

        return mp.iterator();
    }
}
