package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.OrderDTO;
import com.itechart.deliveryservice.controller.data.ShortOrderDTO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.dozer.DozerBeanMapper;
import org.jboss.resteasy.core.Dispatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.jboss.resteasy.mock.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
@Transactional
public class OrderControllerTest {

    @Autowired
    ContactDao contactDao;
    @Autowired
    UserDao userDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    DozerBeanMapper dozer;

    private Dispatcher dispatcher;
    private ObjectMapper mapper = new ObjectMapper();
    private Order order;
    private Order order1;
    private Contact contact;
    private User user;

    @Before
    public final void init() {
        initDb();
    }

    @After
    public final void stop() {
    }

    @Test
    public void shouldReturnOrdersInJSON() throws Exception {

        String val = null;
        {
            MockHttpRequest request = MockHttpRequest.get("/api/orders/");
            MockHttpResponse response = new MockHttpResponse();

            dispatcher.invoke(request, response);

            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            val = response.getContentAsString();
        }
        List<ShortOrderDTO> list = mapper.readValue(val, new TypeReference<List<ShortOrderDTO>>() {
        });
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getState(), order.getState());
        assertEquals(list.get(1).getState(), order1.getState());
    }

    @Test
    public void shouldUpdateOrder() throws Exception {

        OrderDTO orderDTO = dozer.map(order, OrderDTO.class);
        orderDTO.setState(OrderState.CAN_NOT_BE_EXECUTED);
        String body = mapper.writeValueAsString(orderDTO);
        {
            MockHttpRequest request = MockHttpRequest.put("/api/orders/" + order.getId());
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();

            dispatcher.invoke(request, response);

            assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
        }
        Order found = orderDao.getById(order.getId());
        assertEquals(order.getState(), found.getState());
    }

    @Test
    public void shouldDeleteOrder() throws Exception {
        {
            MockHttpRequest request = MockHttpRequest.delete("/api/orders/" + order.getId());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);

            assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
        }
        Order found = orderDao.getById(order.getId());
        assertNull(found);
    }

    @Test
    public void shouldFailValidation() throws Exception {

        OrderDTO orderDTO = dozer.map(order, OrderDTO.class);
        orderDTO.setCost("string");
        String body = mapper.writeValueAsString(orderDTO);
        {
            MockHttpRequest request = MockHttpRequest.put("/api/orders/" + order.getId());
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);

            assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
        }
        Order found = orderDao.getById(order.getId());
        assertNotSame(orderDTO.getCost(), found.getCost());
    }

    private void initDb() {

        dispatcher = MockDispatcherFactory.createDispatcher();
        OrderController obj = new OrderController();
        ReflectionTestUtils.setField(obj, "orderDao", orderDao);
        ReflectionTestUtils.setField(obj, "mapper", dozer);
        dispatcher.getRegistry().addSingletonResource(obj);

        user = new User();
        user.setNickName("receptionManager");
        user.setPassword("sha1PassHere");
        user.setRole(UserRole.ADMINISTRATOR);
        userDao.save(user);

        contact = new Contact();
        contact.setName("customer");
        contact.setSurname("customer");
        contact.setMiddleName("customer");
        contact.setDateOfBirth(new Date());
        contact.setEmail("customer@email.com");
        contactDao.save(contact);

        order = new Order();
        order.setCost("100");
        order.setCustomer(contact);
        order.setReceptionManager(user);
        order.setProcessingManager(user);
        order.setDeliveryManager(user);
        order.setRecipient(contact);
        order.setState(OrderState.NEW);
        order.setDescription("description");
        orderDao.save(order);

        order1 = new Order();
        order1.setCost("150");
        order1.setCustomer(contact);
        order1.setReceptionManager(user);
        order1.setProcessingManager(user);
        order1.setDeliveryManager(user);
        order1.setRecipient(contact);
        order1.setState(OrderState.ACCEPTED);
        order1.setDescription("description");
        orderDao.save(order1);
    }
}
