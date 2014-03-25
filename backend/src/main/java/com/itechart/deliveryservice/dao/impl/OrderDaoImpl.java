package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.entity.Order;

import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends DaoImpl<Order> implements OrderDao {

    public OrderDaoImpl(){
        super(Order.class);
    }

}
