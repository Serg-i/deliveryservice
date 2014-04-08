package com.itechart.deliveryservice.dao;

import java.util.List;

public interface Dao<Type> {

    void save(Type object);

    void merge(Type object);

    void delete(Type object);

    List<Type> getAll();

    Type getById(long id);

    long getCount();

    List<Type> getOffset(int from, int count);

    List<Type> getOrderedOffset(int from, int count, String by, boolean asc);

}