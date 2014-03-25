package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.PhoneDao;
import com.itechart.deliveryservice.entity.Phone;

import org.springframework.stereotype.Repository;

@Repository
public class PhoneDaoImpl extends DaoImpl<Phone> implements PhoneDao {

    public PhoneDaoImpl(){
        super(Phone.class);
    }

}
