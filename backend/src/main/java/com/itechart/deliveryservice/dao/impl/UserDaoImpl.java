package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.User;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends DaoImpl<User> implements UserDao {

    public UserDaoImpl(){
        super(User.class);
    }

}
