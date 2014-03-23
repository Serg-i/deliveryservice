package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.ContactDAO;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.entity.Phone;
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
public class ContactDAOIntegrationTest {
    @Autowired
    ContactDAO contactDAO;

    public  ContactDAOIntegrationTest(){
        super();
    }
    @Test
    @Transactional
    public void shouldCreateOrderInDB() {
        Contact contact = createContact("First", "Contact");
        contactDAO.insert(contact);
        assertTrue(contact.getId() > 0);
    }

    @Test
    @Transactional
    public void shouldFindStoredOrder() {
        Contact contactToStore = createContact("Find", "Contact");
        contactDAO.insert(contactToStore);
        Contact storedContact = contactDAO.find(contactToStore.getId());
        assertNotNull(storedContact);
        assertEquals(contactToStore.getName(), storedContact.getName());
        assertEquals(contactToStore.getSurname(), storedContact.getSurname());
    }

    @Test
    @Transactional
    public void shouldUpdateStateStoredOrder() {
        Contact contact = createContact("Update", "Contact");
        contactDAO.insert(contact);
        Date today = new Date();
        contact.setDateOfBirth(today);
        contactDAO.update(contact);
        Contact storedContact = contactDAO.find(contact.getId());
        assertNotNull(storedContact);
        assertEquals(today, storedContact.getDateOfBirth());
    }

    @Test
    @Transactional
    public void shouldSaveAndAfterDeleteOrder() {
        Contact contact = createContact("Delete", "Contact");
        contactDAO.insert(contact);
        Contact storedContact = contactDAO.find(contact.getId());
        assertNotNull(storedContact);
        contactDAO.delete(contact);
        contact = contactDAO.find(contact.getId());
        assertNull(contact);
    }

    private Contact createContact(String name, String surname){
        Contact contact = new Contact();
//        Address address = new Address();
        contact.setName(name);
        contact.setSurname(surname);
        contact.setMiddleName(name + "123");
        contact.setDateOfBirth(new Date());
        contact.setEmail(surname+"@gmail.com");
//        address.setCity("cc");
//        address.setStreet("ss");
//        address.setHome("1");
//        address.setFlat("11");
//        contact.setAddress(address);
        contact.setPhones(new ArrayList<Phone>());
        return contact;
    }
}
