package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.PhoneDAO;
import com.itechart.deliveryservice.entity.Phone;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PhoneDAOImpl implements PhoneDAO {

	@PersistenceContext
    EntityManager entityManager;

    @Override
    public void insert(Phone phone) {
        entityManager.persist(phone);
        entityManager.flush();
    }

    @Override
    public Phone find(long id) {
        return entityManager.find(Phone.class, id);
    }

    @Override
    public void update(Phone phone) {
        entityManager.merge(phone);
    }

    @Override
    public void delete(Phone phone) {
        entityManager.remove(phone);
    }
	
}
