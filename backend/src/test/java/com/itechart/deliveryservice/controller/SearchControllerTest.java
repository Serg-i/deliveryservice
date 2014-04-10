package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.SearchOrderDTO;
import com.itechart.deliveryservice.controller.data.ShortOrderDTO;
import com.itechart.deliveryservice.controller.data.TableDTO;
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
    public void shouldFindOrders() throws Exception {

        Order order = orderDao.getById(1);

        int countOfHits = 0;
        List<Order> list = orderDao.getAll();
        for (Order o : list)
            if (order.getDate().compareTo(o.getDate()) >= 0
                    && order.getCustomer() == o.getCustomer()
                    && order.getRecipient() == o.getRecipient())
                countOfHits++;

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
        TableDTO<ShortOrderDTO> ret = mapper.readValue(result, new TypeReference<TableDTO<ShortOrderDTO>>() {
        });
        assertEquals(countOfHits, ret.getCurrentPage().size());
        for(ShortOrderDTO o : ret.getCurrentPage()) {
            boolean exist = false;
            assertTrue(order.getDate().compareTo(o.getDate()) >= 0);
            assertEquals(order.getCustomer().getName(), o.getCustomerName());
            assertEquals(order.getRecipient().getName(), o.getRecipientName());
        }
    }

}
