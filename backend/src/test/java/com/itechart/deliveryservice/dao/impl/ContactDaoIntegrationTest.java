package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.entity.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

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
        Contact contact = createContact("First", "Contact");
        contactDao.save(contact);
        assertTrue(contact.getId() > 0);
    }

    @Test
    @Transactional
    public void shouldFindStoredContact() {
        Contact contactToStore = createContact("Find", "Contact");
        contactDao.save(contactToStore);
        Contact storedContact = contactDao.getById(contactToStore.getId());
        assertNotNull(storedContact);
        assertEquals(contactToStore.getName(), storedContact.getName());
        assertEquals(contactToStore.getSurname(), storedContact.getSurname());
    }

    @Test
    @Transactional
    public void shouldUpdateStateStoredContact() {
        Contact contact = createContact("Update", "Contact");
        contactDao.save(contact);
        Date today = new Date();
        contact.setDateOfBirth(today);
        contactDao.merge(contact);
        Contact storedContact = contactDao.getById(contact.getId());
        assertNotNull(storedContact);
        assertEquals(today, storedContact.getDateOfBirth());
    }

    @Test
    @Transactional
    public void shouldSaveAndAfterDeleteContact() {
        Contact contact = createContact("Delete", "Contact");
        contactDao.save(contact);
        Contact storedContact = contactDao.getById(contact.getId());
        assertNotNull(storedContact);
        contactDao.delete(contact);
        contact = contactDao.getById(contact.getId());
        assertNull(contact);
    }

    private Contact createContact(String name, String surname){
        Contact contact = new Contact();
        Address address = new Address();
        contact.setName(name);
        contact.setSurname(surname);
        contact.setMiddleName(name + "123");
        contact.setDateOfBirth(new Date());
        contact.setEmail(surname+"@gmail.com");
        address.setCity("cc");
        address.setStreet("ss");
        address.setHome("1");
        address.setFlat("11");
        contact.setAddress(address);
        contact.setPhones(new ArrayList<Phone>());
        return contact;
    }
}
