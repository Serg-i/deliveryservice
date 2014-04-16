package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.SearchContactDTO;
import com.itechart.deliveryservice.controller.data.ShortContactDTO;
import com.itechart.deliveryservice.controller.data.TableDTO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.entity.Contact;
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
    public void shouldFindContacts() throws Exception {

        Contact contact = contactDao.getById(1);

        SearchContactDTO dto = new SearchContactDTO();
        dto.setDateOfBirth(contact.getDateOfBirth());
        dto.setDateOp(SearchParams.Operator.LESS);
        dto.setName(contact.getName());
        dto.setSurname(contact.getSurname());
        dto.setMiddleName(contact.getMiddleName());
        dto.setCity(contact.getAddress().getCity());
        dto.setStreet(contact.getAddress().getStreet());
        dto.setHome(contact.getAddress().getHome());
        dto.setFlat(contact.getAddress().getFlat());

        String result = null;
        String body = mapper.writeValueAsString(dto);
        {
            MockHttpRequest request = MockHttpRequest.get("/api/search/contacts/1");
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            result = response.getContentAsString();
        }
        TableDTO<ShortContactDTO> ret = mapper.readValue(result, new TypeReference<TableDTO<ShortContactDTO>>() {
        });
        assertTrue(ret.getCurrentPage().size() > 0);
        for(ShortContactDTO o : ret.getCurrentPage()) {
            assertTrue(contact.getDateOfBirth().compareTo(o.getDateOfBirth()) >= 0);
            assertEquals(contact.getName(), o.getName());
            assertEquals(contact.getSurname(), o.getSurname());
            assertEquals(contact.getMiddleName(), o.getMiddleName());
            assertEquals(contact.getAddress().getCity(), o.getCity());
            assertEquals(contact.getAddress().getStreet(), o.getStreet());
            assertEquals(contact.getAddress().getFlat(), o.getFlat());
            assertEquals(contact.getAddress().getHome(), o.getHome());
        }
    }

}
