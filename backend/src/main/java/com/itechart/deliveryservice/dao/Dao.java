package com.itechart.deliveryservice.dao;

import java.util.List;

public interface Dao<Type> {

    void save(Type object);

    void merge(Type object);

    void delete(Type object);

    List<Type> getAll();

    Type getById(long id);

    long getCount();

}