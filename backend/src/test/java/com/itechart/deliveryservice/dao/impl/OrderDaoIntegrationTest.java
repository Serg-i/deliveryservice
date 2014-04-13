package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.Order;
import com.itechart.deliveryservice.entity.OrderState;
import com.itechart.deliveryservice.utils.SearchParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        List<Order> first = orderDao.getOffset(0, (int) count - 1, "date", true);
        List<Order> second = orderDao.getOffset(0, (int) count - 1, "date", false);
        for (int i = 1; i < first.size(); i++)
            assertTrue(first.get(0).getDate().before(first.get(1).getDate()));
        for (int i = 1; i < second.size(); i++)
            assertTrue(second.get(0).getDate().after(second.get(1).getDate()));
    }

    @Test
    @Transactional
    public void shouldFindCorrectCountOfOrders() {

        List<Order> list = orderDao.getAll();
        assertTrue(list.size() > 0);
        int correctCount = 0;
        for(Order d : list)
            if (d.getCost() == list.get(0).getCost())
                ++correctCount;
        SearchParams sp = new SearchParams();
        sp.addParam("cost", list.get(0).getCost());
        assertEquals(correctCount, orderDao.searchCount(sp));
    }

    @Test
    @Transactional
    public void shouldFindOrders() {

        List<Order> list = orderDao.getAll();
        List<Order> toFind = new ArrayList<Order>();
        assertTrue(list.size() > 0);
        for(Order d : list)
            if (d.getCost() == list.get(0).getCost())
                toFind.add(d);
        SearchParams sp = new SearchParams();
        sp.addParam("cost", list.get(0).getCost());
        List<Order> found = orderDao.search(sp, 0, 1);
        assertTrue(found.size() == 1);
        assertTrue(toFind.contains(found.get(0)));
    }

    @Test
    @Transactional
    public void shouldFindAllInCorrectOrder() {

        long count = orderDao.getCount();
        SearchParams sp = new SearchParams();
        List<Order> found = orderDao.search(sp, 0, (int) count - 1, "date", true);
        for (int i = 1; i < found.size(); i++)
            assertTrue(found.get(i-1).getDate().compareTo(found.get(i).getDate()) <= 0);
    }

    @Test
    @Transactional
    public void shouldFindOrdersBeforeTheDate() {

        Order order = orderDao.getById(1);
        assertNotNull(order);
        SearchParams sp = new SearchParams();
        sp.addParam("date", SearchParams.Operator.LESS, order.getDate());
        long count = orderDao.searchCount(sp);
        List<Order> list = orderDao.search(sp, 0, (int)count);
        assertEquals(count, list.size());
        for(Order d : list)
            assertTrue(d.getDate().compareTo(order.getDate()) <= 0);
    }

    @Test
    @Transactional
    public void shouldTestSearchParamsOrFeature() {

        List<Order> orders = orderDao.getAll();
        int right = 0;
        for (Order o : orders)
            if (o.getState() == OrderState.ACCEPTED
                    || o.getState() == OrderState.IN_PROCESSING)
                right++;
        SearchParams sp = new SearchParams();
        sp.addParam("state", OrderState.ACCEPTED);
        sp.addParam("state", OrderState.IN_PROCESSING);
        assertEquals(right, orderDao.searchCount(sp));
    }
}
