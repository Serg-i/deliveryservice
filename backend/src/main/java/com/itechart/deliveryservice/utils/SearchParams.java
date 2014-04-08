package com.itechart.deliveryservice.utils;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SearchParams {

    TreeMap<String, OpAndValue> mp = new TreeMap<String, OpAndValue>();

    public enum Operator {
        EQUAL,
        LESS,
        GREATER
    }
    public class OpAndValue {
        public Operator operator;
        public Object value;
        public OpAndValue(Operator op, Object value) {
            this.operator = op;
            this.value = value;
        }
    }

    public void addParam(String methodName, String value) {

        OpAndValue ov = new OpAndValue(Operator.EQUAL, value);
        mp.put(methodName, ov);
    }

    public void addParam(String methodName, Operator op, Date value) {

        OpAndValue ov = new OpAndValue(op, value);
        mp.put(methodName, ov);
    }

    public Iterator<Map.Entry<String, OpAndValue>> iterator() {

        return mp.entrySet().iterator();
    }
}
