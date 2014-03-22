package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.ContactDAO;
import com.itechart.deliveryservice.entity.Contact;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Repository
public class ContactDAOImpl implements ContactDAO{

    @PersistenceContext
    EntityManager entityManager;

    public ContactDAOImpl(){
        super();
    }

    @Override
    public void insert(Contact contact) {
       entityManager.persist(contact);
       entityManager.flush();
    }

    @Override
    public void update(Contact contact) {
       entityManager.merge(contact);
    }

    @Override
    public void delete(Contact contact) {
       entityManager.remove(contact);
    }

    @Override
    public Contact find(long id) {
        return entityManager.find(Contact.class, id);
    }
}
