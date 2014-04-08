package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.Order;
import com.itechart.deliveryservice.entity.OrderState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public OrderDaoIntegrationTest() {
        super();
    }

    @Test
    @Transactional
    public void shouldCreateOrderInDB() {

        Order order = new Order();
        order.setCost("100");
        order.setCustomer(contactDao.getById(1));
        order.setReceptionManager(userDao.getById(1));
        order.setProcessingManager(userDao.getById(1));
        order.setDeliveryManager(userDao.getById(1));
        order.setRecipient(contactDao.getById(1));
        order.setState(OrderState.NEW);
        order.setDescription("description");
        orderDao.save(order);
        assertTrue(order.getId() > 0);
    }

    @Test
    @Transactional
    public void shouldFindStoredOrder() {

        Order storedOrder = orderDao.getById(1);
        assertNotNull(storedOrder);
    }

    @Test
    @Transactional
    public void shouldUpdateOrder() {

        Order order = orderDao.getById(1);
        order.setState(OrderState.ACCEPTED);
        orderDao.merge(order);
        Order storedOrder = orderDao.getById(order.getId());
        assertNotNull(storedOrder);
        assertEquals(OrderState.ACCEPTED, storedOrder.getState());
    }

    @Test
    @Transactional
    public void shouldDeleteOrder() {

        Order order = orderDao.getById(1);
        Order storedOrder = orderDao.getById(order.getId());
        assertNotNull(storedOrder);
        orderDao.delete(order);
        order = orderDao.getById(order.getId());
        assertNull(order);
    }

    @Test
    @Transactional
    public void shouldGetCorrectOffset() {

        assertTrue(orderDao.getCount() > 3);
        List<Order> first = orderDao.getOffset(0, 2);
        List<Order> second = orderDao.getOffset(1, 2);
        assertEquals(2, first.size());
        assertEquals(2, second.size());
        assertEquals(first.get(1), second.get(0));
    }

    @Test
    @Transactional
    public void shouldGetOffsetInCorrectOrder() {

        long count = orderDao.getCount();
        List<Order> first = orderDao.getOrderedOffset(0, (int)count - 1, "date", true);
        List<Order> second = orderDao.getOrderedOffset(0, (int)count - 1, "date", false);
        for (int i = 1; i < first.size(); i++)
            assertTrue(first.get(0).getDate().before(first.get(1).getDate()));
        for (int i = 1; i < second.size(); i++)
            assertTrue(second.get(0).getDate().after(second.get(1).getDate()));
    }

}
