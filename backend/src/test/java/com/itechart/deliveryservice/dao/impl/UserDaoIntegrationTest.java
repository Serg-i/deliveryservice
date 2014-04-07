package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.entity.User;
import com.itechart.deliveryservice.entity.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
@TransactionConfiguration(defaultRollback=true)
public class UserDaoIntegrationTest {

    @Autowired
    UserDao userDao;

    @Autowired
    ContactDao contactDao;

    public UserDaoIntegrationTest() {
        super();
    }

    @Test
    @Transactional
    public void shouldCreateUserInDB() {

        User user = new User();
        user.setUsername("I'm the first user!");
        user.setPassword("sha1PassHere");
        user.setRole(UserRole.ADMINISTRATOR);
        userDao.save(user);
        assertTrue(user.getId() > 0);
    }

    @Test
    @Transactional
    public void shouldFindStoredUser() {

        User storedUser = userDao.getById(1);
        assertNotNull(storedUser);
    }

    @Test
    @Transactional
    public void shouldUpdateStoredUser() {

        User user = userDao.getById(1);
        user.setRole(UserRole.COURIER);
        userDao.merge(user);
        User storedUser = userDao.getById(user.getId());
        assertNotNull(storedUser);
        assertEquals(UserRole.COURIER, storedUser.getRole());
    }

    @Test
    @Transactional
    public void shouldSaveAndAfterDeleteUser() {

        User user = userDao.getById(1);
        assertNotNull(user);
        userDao.delete(user);
        user = userDao.getById(user.getId());
        assertNull(user);
    }

    @Test
    @Transactional
    public void shouldUpdateContact() {

        User user = userDao.getById(1);
        Contact contact = new Contact();
        contact.setName("Contact");
        contact.setSurname("Lee");
        contact.setEmail("email@email.com");
        user.setContact(contact);
        contactDao.save(contact);
        contact.setSurname("Updated surname");
        userDao.merge(user);
        user = userDao.getById(user.getId());
        assertEquals("Updated surname", user.getContact().getSurname());
    }

    @Test
    @Transactional
    public void shouldFindByUserName() {

        User user = userDao.getById(1);
        assertNotNull(userDao.getByName(user.getUsername()));
    }

}
