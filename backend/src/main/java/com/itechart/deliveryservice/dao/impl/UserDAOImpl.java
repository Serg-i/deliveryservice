package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.UserDAO;
import com.itechart.deliveryservice.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void insert(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    @Override
    public User find(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

}
