package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.OrderHistoryDao;
import com.itechart.deliveryservice.entity.OrderChange;
import com.itechart.deliveryservice.entity.OrderHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
public class OrderHistoryDaoIntegrationTest {

    @Autowired
    private OrderHistoryDao orderHistoryDao;

    @Test
    @Transactional
    public void testInitialisingDao() {
        assertTrue(orderHistoryDao != null);
    }

    @Test
    @Transactional
    public void testSave() {
        final String COMMENT = "comment";
        OrderHistory orderHistory = new OrderHistory();
        OrderChange orderChange = new OrderChange();
        orderChange.setComment(COMMENT);
        orderHistory.getOrderChanges().add(orderChange);
        orderHistoryDao.save(orderHistory);
        assertEquals(orderHistory, orderHistoryDao.getById(orderHistory.getId()));
        orderHistory = orderHistoryDao.getById(orderHistory.getId());
        assertEquals(orderHistory.getOrderChanges().size(), 1);
        assertEquals(orderHistory.getOrderChanges().get(0).getComment(), COMMENT);
    }

    @Test
    @Transactional
    public void testMerge() {
        OrderHistory orderHistory = new OrderHistory();
        orderHistoryDao.save(orderHistory);
        long id = orderHistory.getId();
        assertEquals(orderHistoryDao.getById(id).getOrderChanges().size(), 0);
        orderHistory = new OrderHistory();
        OrderChange orderChange = new OrderChange();
        orderHistory.getOrderChanges().add(orderChange);
        orderHistory.setId(id);
        orderHistoryDao.merge(orderHistory);
        assertEquals(orderHistoryDao.getById(id).getOrderChanges().size(), 1);
    }

    @Test
    @Transactional
    public void testGetAll() {
        OrderHistory orderHistory1 = new OrderHistory();
        OrderHistory orderHistory2 = new OrderHistory();
        orderHistoryDao.save(orderHistory1);
        orderHistoryDao.save(orderHistory2);
        List<OrderHistory> orderHistories = orderHistoryDao.getAll();
        assertTrue(orderHistories.size() == 2);
        assertTrue(orderHistories.contains(orderHistory1));
        assertTrue(orderHistories.contains(orderHistory2));
    }

    @Test
    @Transactional
    public void testDelete() {
        OrderHistory orderHistory = new OrderHistory();
        orderHistoryDao.save(orderHistory);
        List<OrderHistory> orderHistories = orderHistoryDao.getAll();
        assertTrue(orderHistories.contains(orderHistory));
        orderHistoryDao.delete(orderHistory);
        orderHistories = orderHistoryDao.getAll();
        assertFalse(orderHistories.contains(orderHistory));
    }

    @Test
    @Transactional
    public void testCount() {
        OrderHistory orderHistory = new OrderHistory();
        orderHistoryDao.save(orderHistory);
        assertEquals(orderHistoryDao.getCount(), 1);
        orderHistoryDao.delete(orderHistory);
        assertEquals(orderHistoryDao.getCount(), 0);
    }
}
