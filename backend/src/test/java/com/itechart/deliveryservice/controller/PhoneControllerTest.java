package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.PhoneDTO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.PhoneDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.entity.Phone;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.dozer.DozerBeanMapper;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.After;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
@Transactional
public class PhoneControllerTest {

    @Autowired
    ContactDao contactDao;
    @Autowired
    PhoneDao phoneDao;
    @Autowired
    DozerBeanMapper dozer;

    private Dispatcher dispatcher;
    private ObjectMapper mapper = new ObjectMapper();
    private Contact owner;

    @Before
    public final void init() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        PhoneController obj = new PhoneController();
        ReflectionTestUtils.setField(obj, "phoneDao", phoneDao);
        ReflectionTestUtils.setField(obj, "contactDao", contactDao);
        ReflectionTestUtils.setField(obj, "mapper", dozer);
        dispatcher.getRegistry().addSingletonResource(obj);
        initDb();
    }

    @After
    public final void stop() {
    }

    @Test
    public void shouldReturnContactPhonesInJSON() throws Exception {

        String val = null;
        {
            MockHttpRequest request = MockHttpRequest.get("/api/contacts/"+owner.getId()+"/phones");
            MockHttpResponse response = new MockHttpResponse();

            dispatcher.invoke(request, response);

            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            val = response.getContentAsString();
        }
        List<PhoneDTO> list = mapper.readValue(val, new TypeReference<List<PhoneDTO>>() {
        });
        assertEquals(list.size(), owner.getPhones().size());
        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i).getNumber(), owner.getPhones().get(i).getNumber());
            assertEquals(list.get(i).getCountryCode(), owner.getPhones().get(i).getCountryCode());
            assertEquals(list.get(i).getOperatorCode(), owner.getPhones().get(i).getOperatorCode());
        }
    }

    @Test
    public void shouldUpdatePhone() throws Exception {

        Phone phone = owner.getPhones().get(0);
        PhoneDTO phoneDTO = dozer.map(phone, PhoneDTO.class);
        phoneDTO.setNumber("4444444");
        String body = mapper.writeValueAsString(phoneDTO);
        {
            MockHttpRequest request = MockHttpRequest.put("/api/contacts/" + owner.getId()
                    + "/phones/" + phone.getId());
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();

            dispatcher.invoke(request, response);

            assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
        }
        Phone found = phoneDao.getById(phone.getId());
        assertEquals(phoneDTO.getNumber(), found.getNumber());
    }

    @Test
    public void shouldDeletePhone() throws Exception {

        Phone phone = owner.getPhones().get(0);
        {
            MockHttpRequest request = MockHttpRequest.delete("/api/contacts/" + owner.getId()
                    + "/phones/" + phone.getId());
            MockHttpResponse response = new MockHttpResponse();

            dispatcher.invoke(request, response);

            assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
        }
        Phone found = phoneDao.getById(phone.getId());
        assertNull(found);
    }

    @Test
    public void shouldFailValidation() throws Exception {

        Phone phone = owner.getPhones().get(0);
        PhoneDTO phoneDTO = dozer.map(phone, PhoneDTO.class);
        phoneDTO.setNumber("nope");
        String body = mapper.writeValueAsString(phoneDTO);
        {
            MockHttpRequest request = MockHttpRequest.put("/api/contacts/" + owner.getId()
                    + "/phones/" + phone.getId());
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();

            dispatcher.invoke(request, response);

            assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
        }
        Phone found = phoneDao.getById(phone.getId());
        assertNotSame(phoneDTO.getNumber(), found.getNumber());
    }

    private void initDb() {

        owner = contactDao.getById(1);
    }
}
