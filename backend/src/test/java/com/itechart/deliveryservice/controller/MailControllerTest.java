package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.LetterDTO;
import com.itechart.deliveryservice.controller.data.ShortLetterDTO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.utils.Settings;
import com.itechart.deliveryservice.utils.mail.Letter;
import com.itechart.deliveryservice.utils.mail.template.Template;
import com.itechart.deliveryservice.utils.mail.template.templateImpl.NewYearTemplate;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
@Transactional
public class MailControllerTest {

    @Autowired
    ContactDao contactDao;

    private ObjectMapper mapper = new ObjectMapper();
    private Dispatcher dispatcher;
    private Template template1;
    private LetterDTO letterDTO;
    private Contact contact;
    private List<Long> contactIds;

    @Before
    public final void init() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        MailController obj = new MailController();
        ReflectionTestUtils.setField(obj, "contactDao", contactDao);
        dispatcher.getRegistry().addSingletonResource(obj);
        initDb();
    }

    @After
    public final void stop() {
    }

    @Test
    public void shouldReturnContactEmailsInJSON() throws Exception {
        String body = mapper.writeValueAsString(contactIds);
        String val = null;
        {
            MockHttpRequest request = MockHttpRequest.post("/api/email/new");
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            val = response.getContentAsString();
        }

        ShortLetterDTO list = mapper.readValue(val, new TypeReference<ShortLetterDTO>() {
        });
        assertEquals(list.getMails().size(), 1);
        assertEquals(list.getMails().get(0), contact.getEmail());
    }

    @Test
    public void shouldReturnTemplateInJSON() throws Exception {
        String val = null;
        {
            MockHttpRequest request = MockHttpRequest.get("/api/email/template/1");
            request.contentType(MediaType.APPLICATION_JSON);
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            val = response.getContentAsString();
        }

        NewYearTemplate template2 = mapper.readValue(val, new TypeReference<NewYearTemplate>() {
        });
        assertEquals(template1.getName(), template2.getName());
        assertEquals(template1.getTemplateText(), template2.getTemplateText());
    }
    /*
    @Test
    public void shouldSendMailWithoutError() throws Exception {

        String body = mapper.writeValueAsString(letterDTO);
        {
            MockHttpRequest request = MockHttpRequest.post("/api/email/send");
            request.contentType(MediaType.APPLICATION_JSON);
            request.content(body.getBytes());
            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
        }
    }
     */
    private void initDb() {

        contact = contactDao.getById(1);
        contact.setEmail("yandex@gmail.com");
        contactDao.merge(contact);
        contact =  contactDao.getById(1);

        contactIds = new ArrayList<Long>();
        contactIds.add(contact.getId());

        letterDTO = new LetterDTO();
        letterDTO.setText("hi");
        letterDTO.setSubject("hi subj");
        letterDTO.setContactToId(contactIds);
        letterDTO.setTemplate(new Long(1));

        template1 =  new NewYearTemplate();


    }
}
