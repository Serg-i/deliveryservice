package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.entity.Address;
import com.itechart.deliveryservice.entity.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
public class ContactDaoIntegrationTest {

    @Autowired
    ContactDao contactDao;

    public  ContactDaoIntegrationTest(){
        super();
    }

    @Test
    @Transactional
    public void shouldCreateContactInDB() {

        Contact contact = new Contact();
        Address address = new Address();
        contact.setName("name");
        contact.setSurname("surname");
        contact.setMiddleName("middleName");
        contact.setDateOfBirth(new Date());
        contact.setEmail("name@gmail.com");
        address.setCity("cc");
        address.setStreet("ss");
        address.setHome("1");
        address.setFlat("11");
        contact.setAddress(address);
        contactDao.save(contact);
        assertTrue(contact.getId() > 0);
    }

    @Test
    @Transactional
    public void shouldFindStoredContact() {

        Contact storedContact = contactDao.getById(1);
        assertNotNull(storedContact);
    }

    @Test
    @Transactional
    public void shouldUpdateStoredContact() {

        Contact contact = contactDao.getById(1);
        Date today = new Date();
        contact.setDateOfBirth(today);
        contactDao.merge(contact);
        Contact storedContact = contactDao.getById(contact.getId());
        assertNotNull(storedContact);
        assertEquals(today, storedContact.getDateOfBirth());
    }

    @Test
    @Transactional
    public void shouldDeleteContact() {

        Contact storedContact = contactDao.getById(1);
        assertNotNull(storedContact);
        contactDao.delete(storedContact);
        Contact contact = contactDao.getById(storedContact.getId());
        assertNull(contact);
    }

}
