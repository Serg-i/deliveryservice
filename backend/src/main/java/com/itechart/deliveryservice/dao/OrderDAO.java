package com.itechart.deliveryservice.dao;

import com.itechart.deliveryservice.entity.Order;

public interface OrderDAO {

    void insert(Order order);

    Order find(long id);

    void update(Order order);

    void delete(Order order);

}