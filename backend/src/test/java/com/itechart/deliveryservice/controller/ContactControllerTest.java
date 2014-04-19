package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.ContactDTO;
import com.itechart.deliveryservice.controller.data.TableDTO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.entity.Order;
import com.itechart.deliveryservice.entity.User;
import com.itechart.deliveryservice.exceptionhandler.BusinessLogicException;
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
public class ContactControllerTest {

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
    private Contact contact;

    @Before
    public void init(){
        dispatcher = MockDispatcherFactory.createDispatcher();
        ContactController obj = new ContactController();
        ReflectionTestUtils.setField(obj, "contactDao", contactDao);
        ReflectionTestUtils.setField(obj, "mapper", dozer);
        ReflectionTestUtils.setField(obj, "orderDao", orderDao);
        ReflectionTestUtils.setField(obj, "userDao", userDao);
        dispatcher.getRegistry().addSingletonResource(obj);
        contact = contactDao.getById(1);
    }

    @After
    public void stop(){

    }

    @Test
    public void shouldGetContact() throws Exception{
       String val;
        {
            MockHttpRequest request = MockHttpRequest.get("/api/contacts/" + contact.getId());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            val = response.getContentAsString();
        }
        ContactDTO saved = mapper.readValue(val, ContactDTO.class);
        {
            assertEquals(saved.getName(), contactDao.getById(contact.getId()).getName());
            assertEquals(saved.getSurname(),  contactDao.getById(contact.getId()).getSurname());
            assertEquals(saved.getMiddleName(),  contactDao.getById(contact.getId()).getMiddleName());
            assertEquals(saved.getEmail(),  contactDao.getById(contact.getId()).getEmail());
            assertEquals(saved.getDateOfBirth(),  contactDao.getById(contact.getId()).getDateOfBirth());
            assertEquals(saved.getCity(),  contactDao.getById(contact.getId()).getAddress().getCity());
            assertEquals(saved.getStreet(),  contactDao.getById(contact.getId()).getAddress().getStreet());
            assertEquals(saved.getHome(),  contactDao.getById(contact.getId()).getAddress().getHome());
            assertEquals(saved.getFlat(),  contactDao.getById(contact.getId()).getAddress().getFlat());
        }
    }

    @Test
    public void shouldGetAllContacts() throws Exception{
        String val;
        {
            MockHttpRequest request = MockHttpRequest.get("/api/contacts/p/1");
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            val = response.getContentAsString();
        }
       TableDTO<ContactDTO> contacts = mapper.readValue(val, new TypeReference<TableDTO<ContactDTO>>(){});
       assertTrue(contacts.getCount() > 0);
       assertTrue(contacts.getCurrentPage().size() > 0);

    }

    @Test
    public void shouldSaveContact() throws Exception{

        Contact contact = new Contact();
        contact.setName("Name");
        contact.setSurname("Surname");
        ContactDTO contactDTO = dozer.map(contact, ContactDTO.class);
        String val = null;
        String body = mapper.writeValueAsString(contactDTO);
        {
            MockHttpRequest request = MockHttpRequest.post("/api/contacts");
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();

            dispatcher.invoke(request, response);

            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            val = response.getContentAsString();
        }
        ContactDTO saved = mapper.readValue(val, ContactDTO.class);
        Contact found = contactDao.getById(saved.getId());
        assertNotNull(found);
    }

    @Test
    public void shouldUpdateContact() throws Exception{
        ContactDTO contactDTO = dozer.map(contact, ContactDTO.class);
        contactDTO.setCity("minsk");
        String body = mapper.writeValueAsString(contactDTO);
        {
            MockHttpRequest request = MockHttpRequest.put("/api/contacts/" + contact.getId());
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();

            dispatcher.invoke(request, response);

            assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
        }
        Contact found = contactDao.getById(contact.getId());
        assertEquals(contact.getName(), found.getName());

    }

    @Test
    public void shouldDeleteContact() throws Exception{
        Contact del = contactDao.getById(17);
        {
            MockHttpRequest request = MockHttpRequest.delete("/api/contacts/" + del.getId());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
        }
        Contact found = contactDao.getById(del.getId());
        assertNull(found);
    }

    @Test
    public void shouldFailValidation() throws Exception{
        ContactDTO contactDTO = dozer.map(contact, ContactDTO.class);
        contactDTO.setName("123%^&");
        String body = mapper.writeValueAsString(contactDTO);
      {
            MockHttpRequest request = MockHttpRequest.put("/api/contacts/" + contact.getId());
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();

            dispatcher.invoke(request, response);

            assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
        }
        Contact found = contactDao.getById(contact.getId());
        assertNotSame(contactDTO.getName(), found.getName());
    }

    @Test (expected = UnhandledException.class)
    public void shouldDenyDeleteContactFromUser() throws Exception{
        User user =  userDao.getById(1);
        user.setContact(contact);
        {
            MockHttpRequest request = MockHttpRequest.delete("/api/contacts/" + contact.getId());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_METHOD_NOT_ALLOWED, response.getStatus());
        }
        assertNotNull(contactDao.getById(contact.getId()));
    }

    @Test (expected = UnhandledException.class)
    public void shouldDenyDeleteContactFromOrder() throws Exception{
        Order order = orderDao.getById(1);
        order.setCustomer(contact);
        {
            MockHttpRequest request = MockHttpRequest.delete("/api/contacts/" + contact.getId());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_METHOD_NOT_ALLOWED, response.getStatus());
        }

    }


}

