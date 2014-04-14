package com.itechart.deliveryservice.dao;

import com.itechart.deliveryservice.utils.SearchParams;

import java.util.List;

public interface Dao<Type> {

    void save(Type object);

    void merge(Type object);

    void delete(Type object);

    List<Type> getAll();

    Type getById(long id);

    long getCount();

    List<Type> getOffset(int from, int count);

    List<Type> getOffset(int from, int count, String by, boolean asc);

    long searchCount(SearchParams params);

    List<Type> search(SearchParams params, int from, int count);

    List<Type> search(SearchParams params, int from, int count, String by, boolean asc);

    List<Type> searchAll(SearchParams params);
}