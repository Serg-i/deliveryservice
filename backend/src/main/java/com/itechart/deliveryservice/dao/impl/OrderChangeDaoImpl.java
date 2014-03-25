package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.OrderChangeDao;
import com.itechart.deliveryservice.entity.OrderChange;
import org.springframework.stereotype.Repository;

@Repository
public class OrderChangeDaoImpl extends DaoImpl<OrderChange> implements OrderChangeDao {

    public OrderChangeDaoImpl() {
        super(OrderChange.class);
    }

}
