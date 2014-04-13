package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.OrderChangeDTO;
import com.itechart.deliveryservice.controller.data.OrderDTO;
import com.itechart.deliveryservice.controller.data.ShortOrderDTO;
import com.itechart.deliveryservice.controller.data.TableDTO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.*;
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
import java.util.Date;
import java.util.List;

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
    public void shouldReturnOrderChangesInJSON() throws Exception {

        OrderChange orderChange = new OrderChange();
        orderChange.setComment("COMMENT");
        orderChange.setNewState(OrderState.CANCELED);
        orderChange.setDate(new Date());
        orderChange.setUserChangedStatus(userDao.getById(1));
        order.addChange(orderChange);
        String content = null;
        orderDao.save(order);
        {
            MockHttpRequest request = MockHttpRequest.get("/api/orders/" + order.getId() + "/orderChanges");
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            content = response.getContentAsString();
        }
        List<OrderChangeDTO> list = mapper.readValue(content, new TypeReference<List<OrderChangeDTO>>() {
        });
        assertTrue(list.size() > 0);
        assertEquals(orderChange.getComment(), list.get(list.size() - 1).getComment());
        assertEquals(orderChange.getUserChangedStatus().getUsername(), list.get(list.size() - 1).getUserChangedStatusNickname());
        assertEquals(orderChange.getNewState(), list.get(list.size() - 1).getNewState());
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
        assertTrue(table.getPagesCount() > 0);
        assertTrue(table.getCurrentPage().size() > 0);
    }

/*    @Test
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
    }*/

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

/*    @Test
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
    }*/

    private void initDb() {

        dispatcher = MockDispatcherFactory.createDispatcher();
        OrderController obj = new OrderController();
        ReflectionTestUtils.setField(obj, "orderDao", orderDao);
        ReflectionTestUtils.setField(obj, "userDao", userDao);
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
