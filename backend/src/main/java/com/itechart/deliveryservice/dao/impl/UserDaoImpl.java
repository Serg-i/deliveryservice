package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.User;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class UserDaoImpl extends DaoImpl<User> implements UserDao {

    public UserDaoImpl(){
        super(User.class);
    }

    public User getByName(String name) {

        Query q = getEntityManager().createQuery("select c from User c where c.username = :name");
        q.setParameter("name", name);
        return (User)q.getSingleResult();
    }

}
