package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.*;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.OrderChangeDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.entity.Order;
import com.itechart.deliveryservice.entity.OrderState;
import com.itechart.deliveryservice.entity.User;
import com.itechart.deliveryservice.utils.SearchParams;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.dozer.DozerBeanMapper;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.UnhandledException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.*;

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
    OrderChangeDao orderChangeDao;
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
    public void shouldReturnOrder() throws Exception{

        String val = null;
        {
            MockHttpRequest request = MockHttpRequest.get("/api/orders/" + order.getId());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            val = response.getContentAsString();
        }
        OrderDTO found = mapper.readValue(val, new TypeReference<OrderDTO>() {
        });
        assertEquals(found.getId(), order.getId());
    }

    @Test(expected = UnhandledException.class)
    public void shouldNotAllowUserToSeeOrder() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user4", "1234"));
        MockHttpRequest request = MockHttpRequest.get("/api/orders/" + order.getId());
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
    }

    @Test
    public void shouldReturnOrdersForCourier() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user4", "1234"));
        String val = null;
        {
            MockHttpRequest request = MockHttpRequest.get("/api/orders/p/1");
            MockHttpResponse response = new MockHttpResponse();

            dispatcher.invoke(request, response);

            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            val = response.getContentAsString();
        }
        TableDTO<ShortOrderDTO> table = mapper.readValue(val,
                new TypeReference<TableDTO<ShortOrderDTO>>() {
        });
        assertTrue(table.getCount() > 0);
        assertTrue(table.getCurrentPage().size() > 0);
    }

    @Test
    public void shouldCreateOrder() throws Exception {

        ReceiveOrderDTO order = new ReceiveOrderDTO();
        order.setCost("123");
        assertNotNull(userDao.getById(1));
        order.setCustomerId(1);
        order.setRecipientId(1);
        order.setDeliveryManagerId(1);
        order.setProcessingManagerId(1);
        order.setDescription("create_test");
        String body = mapper.writeValueAsString(order);
        {
            MockHttpRequest request = MockHttpRequest.post("/api/orders/");
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
        }
        SearchParams sp = new SearchParams();
        sp.addParam("description", "create_test");
        assertTrue(orderDao.searchCount(sp) > 0);
    }

    @Test(expected = UnhandledException.class)
    public void shouldFailToCreateOrder() throws Exception {

        ReceiveOrderDTO order = new ReceiveOrderDTO();
        order.setCost("123");
        order.setCustomerId(1111);
        order.setRecipientId(1111);
        order.setDeliveryManagerId(1111);
        order.setProcessingManagerId(1111);
        order.setDescription("create_test");
        String body = mapper.writeValueAsString(order);
        {
            MockHttpRequest request = MockHttpRequest.post("/api/orders/");
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
        }
    }

    @Test
    public void shouldUpdateOrder() throws Exception {

        ReceiveOrderDTO orderDTO = dozer.map(order, ReceiveOrderDTO.class);
        assertNotNull(userDao.getById(2));
        orderDTO.setDescription("new");
        orderDTO.setProcessingManagerId(2);
        orderDTO.setDeliveryManagerId(2);
        orderDTO.setCustomerId(2);
        orderDTO.setRecipientId(2);
        orderDTO.setCost("123");
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
        assertEquals("new", found.getDescription());
        assertEquals("123", found.getCost());
        assertEquals(2, found.getCustomer().getId());
        assertEquals(2, found.getRecipient().getId());
        assertEquals(2, found.getDeliveryManager().getId());
        assertEquals(2, found.getProcessingManager().getId());
    }

    @Test
    public void shouldDeleteOrder() throws Exception {

        Order order = orderDao.getById(3);
        assertNotNull(order);
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
    public void shouldUpdateOrderStateAndAddChangeToHistory() throws Exception {

        int history = order.getChanges().size();
        NewStateDTO st = new NewStateDTO();
        st.setComment("comment");
        st.setNewState(OrderState.CAN_NOT_BE_EXECUTED);
        String body = mapper.writeValueAsString(st);
        {
            MockHttpRequest request = MockHttpRequest.put("/api/orders/" + order.getId() + "/state");
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
        }
        Order found = orderDao.getById(order.getId());
        assertEquals(OrderState.CAN_NOT_BE_EXECUTED, found.getState());
        assertEquals(history + 1, found.getChanges().size());
    }

    private void initDb() {

        dispatcher = MockDispatcherFactory.createDispatcher();
        OrderController obj = new OrderController();
        ReflectionTestUtils.setField(obj, "orderDao", orderDao);
        ReflectionTestUtils.setField(obj, "userDao", userDao);
        ReflectionTestUtils.setField(obj, "orderChangeDao", orderChangeDao);
        ReflectionTestUtils.setField(obj, "mapper", dozer);
        //this user should be in import.sql with role - ADMINISTRATOR
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("admin", "1234"));
        dispatcher.getRegistry().addSingletonResource(obj);

        user = userDao.getById(1);
        contact = contactDao.getById(1);
        order = orderDao.getById(1);
        order1 = orderDao.getById(2);
    }
}
