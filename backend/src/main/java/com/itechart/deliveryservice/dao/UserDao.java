package com.itechart.deliveryservice.dao;

import com.itechart.deliveryservice.entity.User;

public interface UserDao extends Dao<User>{

    public User getByName(String name);
}
