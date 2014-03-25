package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.UserDao;
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

    public UserDaoIntegrationTest() {
        super();
    }

    @Test
    @Transactional
    public void shouldCreateUserInDB() {
        User user = createUser("I'm the first user!");
        userDao.save(user);
        assertTrue(user.getId() > 0);
    }

    @Test
    @Transactional
    public void shouldFindStoredUser() {
        User userToStore = createUser("FindMe");
        userDao.save(userToStore);
        User storedUser = userDao.getById(userToStore.getId());
        assertNotNull(storedUser);
        assertEquals(userToStore.getNickName(), storedUser.getNickName());
    }

    @Test
    @Transactional
    public void shouldUpdateStoredUser() {
        User user = createUser("UpdateMePls");
        userDao.save(user);
        user.setRole(UserRole.COURIER);
        userDao.merge(user);
        User storedUser = userDao.getById(user.getId());
        assertNotNull(storedUser);
        assertEquals(UserRole.COURIER, storedUser.getRole());
    }

    @Test
    @Transactional
    public void shouldSaveAndAfterDeleteUser() {
        User user = createUser("DEL me pls!");
        userDao.save(user);
        User storedUser = userDao.getById(user.getId());
        assertNotNull(storedUser);
        userDao.delete(user);
        user = userDao.getById(user.getId());
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
