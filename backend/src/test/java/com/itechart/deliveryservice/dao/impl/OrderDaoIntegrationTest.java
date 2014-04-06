package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
public class OrderDaoIntegrationTest {

    @Autowired
    OrderDao orderDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ContactDao contactDao;

    private Contact contact;
    private User user;


    public OrderDaoIntegrationTest() {
        super();
    }

    @Before
    public final void before() {

        user = new User();
        user.setUsername("processingManager");
        user.setPassword("sha2PassHere");
        user.setRole(UserRole.ADMINISTRATOR);
        userDao.save(user);

        contact = new Contact();
        contact.setName("info");
        contact.setSurname("info");
        contact.setMiddleName("info");
        contact.setDateOfBirth(new Date());
        contact.setEmail("info@gmail.com");
        contactDao.save(contact);

    }

    @Test
    @Transactional
    public void shouldCreateOrderInDB() {
        Order order = createOrder("It's the first order!");
        orderDao.save(order);
        assertTrue(order.getId() > 0);
    }

    @Test
    @Transactional
    public void shouldFindStoredOrder() {
        Order orderToStore = createOrder("FindOrder");
        orderDao.save(orderToStore);
        Order storedOrder = orderDao.getById(orderToStore.getId());
        assertNotNull(storedOrder);
        assertEquals(orderToStore.getCustomer(), storedOrder.getCustomer());
        assertEquals(orderToStore.getRecipient(), storedOrder.getRecipient());
    }

    @Test
    @Transactional
    public void shouldUpdateStateStoredOrder() {
        Order order = createOrder("UpdateOrder");
        orderDao.save(order);
        order.setState(OrderState.ACCEPTED);
        orderDao.merge(order);
        Order storedOrder = orderDao.getById(order.getId());
        assertNotNull(storedOrder);
        assertEquals(OrderState.ACCEPTED, storedOrder.getState());
    }

    @Test
    @Transactional
    public void shouldSaveAndAfterDeleteOrder() {
        Order order = createOrder("DelOrder!");
        orderDao.save(order);
        Order storedOrder = orderDao.getById(order.getId());
        assertNotNull(storedOrder);
        orderDao.delete(order);
        order = orderDao.getById(order.getId());
        assertNull(order);
    }

    private Order createOrder(String description) {
        Order order = new Order();
        order.setCost("100");
        order.setCustomer(contact);
        order.setReceptionManager(user);
        order.setProcessingManager(user);
        order.setDeliveryManager(user);
        order.setRecipient(contact);
        order.setState(OrderState.NEW);
        order.setDescription(description);
        return order;
    }

}
