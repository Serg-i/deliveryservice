package com.itechart.deliveryservice.controller;


import com.itechart.deliveryservice.controller.data.*;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.entity.User;
import com.itechart.deliveryservice.entity.UserRole;
import com.itechart.deliveryservice.utils.SearchParams;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
@Transactional
public class UserControllerTest {

    @Autowired
    UserDao userDao;
    @Autowired
    ContactDao contactDao;
    @Autowired
    DozerBeanMapper dozer;

    private Dispatcher dispatcher;
    private ObjectMapper mapper = new ObjectMapper();
    private User user;

    @Before
    public final void init() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        UserController userController=new UserController();
        ReflectionTestUtils.setField(userController, "userDao", userDao);
        ReflectionTestUtils.setField(userController,"contactDao",contactDao);
        ReflectionTestUtils.setField(userController, "mapper", dozer);
        dispatcher.getRegistry().addSingletonResource(userController);
        user=userDao.getById(1);
    }

    @After
    public final void stop() {
    }


    @Test
    public void shouldGetUsersPage() throws Exception{
        String val;
        {
            MockHttpRequest request = MockHttpRequest.get("/api/users/p/1");
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            val = response.getContentAsString();
        }
        TableDTO<UserViewDTO> contacts = mapper.readValue(val, new TypeReference<TableDTO<UserViewDTO>>(){});
        assertTrue(contacts.getCount() > 0);
        assertTrue(contacts.getCurrentPage().size() > 0);

    }
    @Test
    public void shouldGetUser() throws Exception {
        String val;
        {
            MockHttpRequest request = MockHttpRequest.get("/api/users/" + user.getId());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            val = response.getContentAsString();
        }
        UserViewDTO received = mapper.readValue(val, UserViewDTO.class);
        assertEquals(user.getId(),received.getId());
        assertEquals(user.getUsername(),received.getUsername());
        assertEquals(user.getPassword(),received.getPassword());
        assertEquals(user.getRole(),received.getRole());
        Contact contact=user.getContact();
        assertEquals(contact.getId(),received.getContactId());

    }

    @Test
    public void shouldDeleteUser() throws Exception{
        MockHttpRequest request = MockHttpRequest.delete("/api/users/" + user.getId());
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
        User found = userDao.getById(user.getId());
        assertNull(found);
    }


    @Test
    public void shouldUpdateUser() throws Exception{
        UserModifyDTO userModifyDTO =dozer.map(user,UserModifyDTO.class);
        userModifyDTO.setUsername("Rondo");
        String body = mapper.writeValueAsString(userModifyDTO);
        {
            MockHttpRequest request = MockHttpRequest.put("/api/users/" + user.getId());
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
        }
        User found = userDao.getById(user.getId());
        assertEquals(userModifyDTO.getUsername(),found.getUsername());
    }

    @Test
    public void shouldCreateUser() throws Exception{
        UserCreateDTO user=new UserCreateDTO();
        user.setUsername("new user");
        user.setPassword("1111111");
        user.setRole(UserRole.ADMINISTRATOR);
        assertNotNull(contactDao.getById(1));
        user.setContactId(1);
        String body = mapper.writeValueAsString(user);
        {
            MockHttpRequest request = MockHttpRequest.post("/api/users/");
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
        }
        SearchParams sp=new SearchParams();
        sp.addParam("username",user.getUsername());
        sp.addParam("password",user.getPassword());
        sp.addParam("role",user.getRole());
        sp.addParam("contact",contactDao.getById(user.getContactId()));
        assertTrue(userDao.searchCount(sp) == 1);
    }

    @Test
    public void shouldFailToCreateUser() throws Exception {
        UserCreateDTO user=new UserCreateDTO();
        user.setUsername("new user");
        user.setPassword("111");
        user.setRole(UserRole.ADMINISTRATOR);
        assertNotNull(contactDao.getById(1));
        user.setContactId(1);
        String body = mapper.writeValueAsString(user);
        {
            MockHttpRequest request = MockHttpRequest.post("/api/users/");
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
        }
        SearchParams sp=new SearchParams();
        sp.addParam("username",user.getUsername());
        sp.addParam("password",user.getPassword());
        sp.addParam("role",user.getRole());
        sp.addParam("contact",contactDao.getById(user.getContactId()));
        assertTrue(userDao.searchCount(sp) == 0);
    }



}
