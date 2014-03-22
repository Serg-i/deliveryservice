package com.itechart.deliveryservice.dao;

import com.itechart.deliveryservice.entity.Contact;


public interface ContactDAO {
    void insert(Contact contact);
    void update(Contact contact);
    void delete(Contact contact);
    Contact find(long id);
}
