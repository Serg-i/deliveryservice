package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.OrderChangeDao;
import com.itechart.deliveryservice.entity.OrderChange;
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
public class OrderChangeDaoIntegrationTest {

    @Autowired
    private OrderChangeDao orderChangeDao;

    @Test
    @Transactional
    public void testInitialisingDao() {
        assertTrue(orderChangeDao != null);
    }

    @Test
    @Transactional
    public void testSave() {
        OrderChange orderChange = new OrderChange();
        orderChangeDao.save(orderChange);
        assertEquals(orderChange, orderChangeDao.getById(orderChange.getId()));
    }

    @Test
    @Transactional
    public void testMerge() {
        final String COMMENT = "comment";
        final String COMMENT_2 = "comment 2";
        OrderChange orderChange = new OrderChange();
        orderChange.setComment(COMMENT);
        orderChangeDao.save(orderChange);
        long id = orderChange.getId();
        assertEquals(orderChangeDao.getById(id).getComment(), COMMENT);
        orderChange = new OrderChange();
        orderChange.setComment(COMMENT_2);
        orderChange.setId(id);
        orderChangeDao.merge(orderChange);
        assertEquals(orderChangeDao.getById(id).getComment(), COMMENT_2);
    }

    @Test
    @Transactional
    public void testGetAll() {
        OrderChange orderChange1 = new OrderChange();
        OrderChange orderChange2 = new OrderChange();
        orderChangeDao.save(orderChange1);
        orderChangeDao.save(orderChange2);
        List<OrderChange> orderChanges = orderChangeDao.getAll();
        assertTrue(orderChanges.size() == 2);
        assertTrue(orderChanges.contains(orderChange1));
        assertTrue(orderChanges.contains(orderChange2));
    }

    @Test
    @Transactional
    public void testDelete() {
        OrderChange orderChange = new OrderChange();
        orderChangeDao.save(orderChange);
        List<OrderChange> orderChanges = orderChangeDao.getAll();
        assertTrue(orderChanges.contains(orderChange));
        orderChangeDao.delete(orderChange);
        orderChanges = orderChangeDao.getAll();
        assertFalse(orderChanges.contains(orderChange));
    }

    @Test
    @Transactional
    public void testCount() {
        OrderChange orderChange = new OrderChange();
        orderChangeDao.save(orderChange);
        assertEquals(orderChangeDao.getCount(), 1);
        orderChangeDao.delete(orderChange);
        assertEquals(orderChangeDao.getCount(), 0);
    }
}
