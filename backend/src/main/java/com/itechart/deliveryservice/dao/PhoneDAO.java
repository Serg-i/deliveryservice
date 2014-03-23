package com.itechart.deliveryservice.dao;

import com.itechart.deliveryservice.entity.Phone;

public interface PhoneDAO {

    void insert(Phone phone);

    Phone find(long id);

    void update(Phone phone);

    void delete(Phone phone);

}
