package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.OrderDAO;
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
public class OrderDAOIntegrationTest {

    @Autowired
    OrderDAO orderDAO;

    public OrderDAOIntegrationTest() {
        super();
    }

    @Test
    @Transactional
    public void shouldCreateOrderInDB() {
        Order order = createOrder("It's the first order!");
        orderDAO.insert(order);
        assertTrue(order.getId() > 0);
    }

    @Test
    @Transactional
    public void shouldFindStoredOrder() {
        Order orderToStore = createOrder("FindOrder");
        orderDAO.insert(orderToStore);
        Order storedUser = orderDAO.find(orderToStore.getId());
        assertNotNull(storedUser);
        assertEquals(orderToStore.getCustomer(), storedUser.getCustomer());
        assertEquals(orderToStore.getRecipient(), storedUser.getRecipient());
    }

    @Test
    @Transactional
    public void shouldUpdateStateStoredOrder() {
        Order order = createOrder("UpdateOrder");
        orderDAO.insert(order);
        order.setState(OrderState.ACCEPTED);
        orderDAO.update(order);
        Order storedOrder = orderDAO.find(order.getId());
        assertNotNull(storedOrder);
        assertEquals(OrderState.ACCEPTED, storedOrder.getState());
    }

    @Test
    @Transactional
    public void shouldSaveAndAfterDeleteOrder() {
        Order order = createOrder("DelOrder!");
        orderDAO.insert(order);
        Order storedOrder = orderDAO.find(order.getId());
        assertNotNull(storedOrder);
        orderDAO.delete(order);
        order = orderDAO.find(order.getId());
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
