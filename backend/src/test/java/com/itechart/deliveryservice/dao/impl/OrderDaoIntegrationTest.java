package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.entity.Order;
import com.itechart.deliveryservice.entity.OrderState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
public class OrderDaoIntegrationTest {

    @Autowired
    OrderDao orderDao;

    public OrderDaoIntegrationTest() {
        super();
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
        Order storedUser = orderDao.getById(orderToStore.getId());
        assertNotNull(storedUser);
        assertEquals(orderToStore.getCustomer(), storedUser.getCustomer());
        assertEquals(orderToStore.getRecipient(), storedUser.getRecipient());
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
        order.setCost(100);
        order.setCustomer(1);
        order.setReceptionManager(2);
        order.setProcessingManager(3);
        order.setDeliveryManager(4);
        order.setRecipient(5);
        order.setState(OrderState.NEW);
        order.setDescription(description);
        return order;
    }

}
