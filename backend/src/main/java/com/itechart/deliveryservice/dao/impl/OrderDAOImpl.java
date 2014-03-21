package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.OrderDAO;
import com.itechart.deliveryservice.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderDAOImpl implements OrderDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void insert(Order order) {
        entityManager.persist(order);
        entityManager.flush();
    }

    @Override
    public Order find(long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public void update(Order order) {
        entityManager.merge(order);
    }

    @Override
    public void delete(Order order) {
        entityManager.remove(order);
    }
}
