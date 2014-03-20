package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.UserDAO;
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
public class UserDAOIntegrationTest {

    @Autowired
    UserDAO userDAO;

    public UserDAOIntegrationTest() {
        super();
    }

    @Test
    @Transactional
    public void shouldCreateUserInDB() {
        User user = createUser("I'm the first user!");
        userDAO.insert(user);
        assertTrue(user.getId() > 0);
    }

    @Test
    @Transactional
    public void shouldFindStoredUser() {
        User userToStore = createUser("FindMe");
        userDAO.insert(userToStore);
        User storedUser = userDAO.find(userToStore.getId());
        assertNotNull(storedUser);
        assertEquals(userToStore.getNickName(), storedUser.getNickName());
    }

    @Test
    @Transactional
    public void shouldUpdateStoredUser() {
        User user = createUser("UpdateMePls");
        userDAO.insert(user);
        user.setRole(UserRole.COURIER);
        userDAO.update(user);
        User storedUser = userDAO.find(user.getId());
        assertNotNull(storedUser);
        assertEquals(UserRole.COURIER, storedUser.getRole());
    }

    @Test
    @Transactional
    public void shouldSaveAndAfterDeleteUser() {
        User user = createUser("DEL me pls!");
        userDAO.insert(user);
        User storedUser = userDAO.find(user.getId());
        assertNotNull(storedUser);
        userDAO.delete(user);
        user = userDAO.find(user.getId());
        assertNull(user);
    }

    private User createUser(String name) {
        User user = new User();
        user.setNickName(name);
        user.setPassword("sha1PassHere");
        user.setRole(UserRole.ADMINISTRATOR);
        return user;
    }

}
