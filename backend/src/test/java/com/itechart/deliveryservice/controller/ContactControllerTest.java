package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.ContactDTO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.entity.Address;
import com.itechart.deliveryservice.entity.Contact;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
@Transactional
public class ContactControllerTest {

    @Autowired
    ContactDao contactDao;
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
        dispatcher.getRegistry().addSingletonResource(obj);
        setValues("init");
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
            MockHttpRequest request = MockHttpRequest.get("/api/contacts");
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            val = response.getContentAsString();
        }
        List<ContactDTO> contacts = mapper.readValue(val, new TypeReference<List<ContactDTO>>() {
        });
        assertEquals(contacts.size(), contactDao.getAll().size());
        for (int i = 0; i < contacts.size(); i++) {
            assertEquals(contacts.get(i).getName(), contactDao.getAll().get(i).getName());
            assertEquals(contacts.get(i).getSurname(), contactDao.getAll().get(i).getSurname());
            assertEquals(contacts.get(i).getMiddleName(), contactDao.getAll().get(i).getMiddleName());
            assertEquals(contacts.get(i).getEmail(), contactDao.getAll().get(i).getEmail());
            assertEquals(contacts.get(i).getDateOfBirth(), contactDao.getAll().get(i).getDateOfBirth());
            assertEquals(contacts.get(i).getCity(), contactDao.getAll().get(i).getAddress().getCity());
            assertEquals(contacts.get(i).getStreet(), contactDao.getAll().get(i).getAddress().getStreet());
            assertEquals(contacts.get(i).getHome(), contactDao.getAll().get(i).getAddress().getHome());
            assertEquals(contacts.get(i).getFlat(), contactDao.getAll().get(i).getAddress().getFlat());
        }
    }

   /* @Test
    public void shouldSaveContact() throws Exception{

        ContactDTO contactDTO = dozer.map(contact, ContactDTO.class);

        String body = mapper.writeValueAsString(contactDTO);
        {
            MockHttpRequest request = MockHttpRequest.post("/api/contacts");
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();

            dispatcher.invoke(request, response);

            assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
        }
        Contact found = contactDao.getById(contact.getId());

        assertNotNull(found);

    }
     */
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
        {
            MockHttpRequest request = MockHttpRequest.delete("/api/contacts/" + contact.getId());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
        }
        Contact found = contactDao.getById(contact.getId());
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

    private void setValues(String surname){
        contact = new Contact();
        Address address = new Address();
        contact.setSurname(surname);
        contact.setName("name");
        contact.setMiddleName("middlename");
        contact.setDateOfBirth(new Date());
        contact.setEmail(surname + "@gmail.com");
        address.setCity("city");
        address.setStreet("street");
        address.setHome("1");
        address.setFlat("11a");
        contact.setAddress(address);
        contactDao.save(contact);
    }
}

