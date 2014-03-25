package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.OrderHistoryDao;
import com.itechart.deliveryservice.entity.OrderHistory;
import org.springframework.stereotype.Repository;

@Repository
public class OrderHistoryDaoImpl extends DaoImpl<OrderHistory> implements OrderHistoryDao {

    public OrderHistoryDaoImpl() {
        super(OrderHistory.class);
    }

}
