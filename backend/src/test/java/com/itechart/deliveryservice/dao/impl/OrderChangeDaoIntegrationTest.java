package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.OrderChangeDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.OrderChange;
import com.itechart.deliveryservice.entity.OrderState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
public class OrderChangeDaoIntegrationTest {

    @Autowired
    private OrderChangeDao orderChangeDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    public void testInitialisingDao() {
        assertTrue(orderChangeDao != null);
    }

    @Test
    @Transactional
    public void testSave() {

        OrderChange orderChange = new OrderChange();
        orderChange.setDate(new Date());
        orderChange.setNewState(OrderState.NEW);
        orderChange.setOrder(orderDao.getById(1));
        orderChange.setUserChangedStatus(userDao.getById(1));
        orderChangeDao.save(orderChange);
        assertTrue(orderChange.getId() > 0);
        assertEquals(orderChange, orderChangeDao.getById(orderChange.getId()));
    }

    @Test
    @Transactional
    public void testMerge() {

        OrderChange orderChange = orderChangeDao.getById(1);
        final String COMMENT = "comment";
        orderChange.setComment(COMMENT);
        orderChangeDao.merge(orderChange);
        assertEquals(orderChangeDao.getById(1).getComment(), COMMENT);
    }

    @Test
    @Transactional
    public void testGetAll() {

        List<OrderChange> orderChanges = orderChangeDao.getAll();
        assertTrue(orderChanges.size() > 0);
    }

    @Test
    @Transactional
    public void testDelete() {

        OrderChange orderChange = orderChangeDao.getById(1);
        assertNotNull(orderChange);
        orderChangeDao.delete(orderChange);
        assertNull(orderChangeDao.getById(orderChange.getId()));
    }

    @Test
    @Transactional
    public void testCount() {

        assertTrue(orderChangeDao.getCount() > 0);
    }
}
