package com.itechart.deliveryservice.dao;

import java.util.List;
import java.util.Map;

public interface Dao<Type> {

    void save(Type object);

    void merge(Type object);

    void delete(Type object);

    List<Type> getAll();

    Type getById(long id);

    long getCount();

    List<Type> getOffset(int from, int count);

    List<Type> getOffset(int from, int count, String by, boolean asc);

    long searchCount(Map<String, String> values);

    List<Type> search(Map<String, String> values, int from, int count);

    List<Type> search(Map<String, String> values, int from, int count, String by, boolean asc);
}