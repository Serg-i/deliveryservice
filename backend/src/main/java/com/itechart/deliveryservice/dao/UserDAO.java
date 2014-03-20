package com.itechart.deliveryservice.dao;

import com.itechart.deliveryservice.entity.User;

public interface UserDAO {

    void insert(User user);

    User find(long id);

    void update(User user);

    void delete(User user);

}
