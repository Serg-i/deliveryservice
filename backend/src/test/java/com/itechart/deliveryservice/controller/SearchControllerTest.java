package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.CountOfRowsDTO;
import com.itechart.deliveryservice.controller.data.SearchOrderDTO;
import com.itechart.deliveryservice.controller.data.ShortOrderDTO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.entity.Order;
import com.itechart.deliveryservice.utils.SearchParams;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.dozer.DozerBeanMapper;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
@Transactional
public class SearchControllerTest {

    @Autowired
    OrderDao orderDao;
    @Autowired
    ContactDao contactDao;
    @Autowired
    DozerBeanMapper dozer;

    private Dispatcher dispatcher;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public final void init() {

        dispatcher = MockDispatcherFactory.createDispatcher();
        SearchController obj = new SearchController();
        ReflectionTestUtils.setField(obj, "orderDao", orderDao);
        ReflectionTestUtils.setField(obj, "contactDao", contactDao);
        ReflectionTestUtils.setField(obj, "mapper", dozer);
        dispatcher.getRegistry().addSingletonResource(obj);
    }

    @Test
    public void shouldReturnCountOfFoundOrders() throws Exception {

        Order order = orderDao.getById(1);
        List<Order> list = orderDao.getAll();
        int count = 0;
        for (Order d : list)
            if (d.getDate().compareTo(order.getDate()) <= 0)
                count++;
        SearchOrderDTO dto = new SearchOrderDTO();
        dto.setDate(order.getDate());
        dto.setDateOp(SearchParams.Operator.LESS);
        String result = null;
        String body = mapper.writeValueAsString(dto);
        {
            MockHttpRequest request = MockHttpRequest.get("/api/search/orders/count");
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            result = response.getContentAsString();
        }
        CountOfRowsDTO ret = mapper.readValue(result, new TypeReference<CountOfRowsDTO>() {
        });
        assertEquals(count, ret.getCount());
    }

    @Test
    public void shouldReturnFoundOrders() throws Exception {

        Order order = orderDao.getById(1);
        SearchOrderDTO dto = new SearchOrderDTO();
        dto.setDate(order.getDate());
        dto.setDateOp(SearchParams.Operator.LESS);
        dto.setCustomer(order.getCustomer().getId());
        dto.setRecipient(order.getRecipient().getId());
        String result = null;
        String body = mapper.writeValueAsString(dto);
        {
            MockHttpRequest request = MockHttpRequest.get("/api/search/orders/1");
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            result = response.getContentAsString();
        }
        List<ShortOrderDTO> ret = mapper.readValue(result, new TypeReference<List<ShortOrderDTO>>() {
        });
        assertTrue(ret.size() > 0);
        for(ShortOrderDTO o : ret) {
            boolean exist = false;

            assertTrue(order.getDate().compareTo(o.getDate()) >= 0);
            assertEquals(order.getCustomer().getName(), o.getCustomerName());
            assertEquals(order.getRecipient().getName(), o.getRecipientName());
        }
    }

}
