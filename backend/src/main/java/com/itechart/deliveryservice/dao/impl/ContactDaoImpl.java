package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.entity.Contact;

import org.springframework.stereotype.Repository;

@Repository
public class ContactDaoImpl extends DaoImpl<Contact> implements ContactDao{

    public ContactDaoImpl(){
        super(Contact.class);
    }

}
