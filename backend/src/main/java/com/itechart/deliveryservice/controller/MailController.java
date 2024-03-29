package com.itechart.deliveryservice.controller;
import com.itechart.deliveryservice.controller.data.LetterDTO;
import com.itechart.deliveryservice.controller.data.ShortLetterDTO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.utils.Settings;
import com.itechart.deliveryservice.utils.mail.Letter;
import com.itechart.deliveryservice.utils.mail.Sender;
import com.itechart.deliveryservice.utils.mail.template.Template;
import com.itechart.deliveryservice.utils.mail.template.TemplateStorage;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/api/email/")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ValidateRequest
public class MailController {


    @Autowired
    private ContactDao contactDao;

    private static final Logger logger = LoggerFactory.getLogger(MailController.class);

    @POST
    @Path("/send")
    public void send(@Valid LetterDTO letterDTO) {

        logger.info("MAIL - SEND");
        List<Contact> contacts = new ArrayList<Contact>();
        for(Long aLong : letterDTO.getContactToId()){
            contacts.add(contactDao.getById(aLong.longValue()));
        }

        Letter letter = new Letter();

        letter.setAddressFrom(Settings.getMailAddressFrom());
        letter.setPassword(Settings.getMailPassword());

        letter.setContactTo(contacts);
        letter.setSubject(letterDTO.getSubject());
        Template template = new TemplateStorage().getTemplate(letterDTO.getTemplate());
        letter.setTemplate(template);
        letter.setText(letterDTO.getText());

        Sender sender = new Sender(letter);
        sender.start();
    }

    @POST
    @Path("/new")
    public ShortLetterDTO getEmails(List<Long> tempIds) {

        logger.info("MAIL - GET MAILS AND TEMPLATES");
        ShortLetterDTO out = new ShortLetterDTO();
        List<String> emails = new ArrayList<String>();

        for(Long aLong : tempIds){
            Contact contact = contactDao.getById(aLong.longValue());
            emails.add(contact.getEmail());
        }
        out.setMails(emails);
        out.setTemplateNames(new TemplateStorage().getAll());
        return out;
    }

    @GET
    @Path("/template/{tempId}")
    public Template getTemplate(@PathParam("tempId") long tempId) {

        logger.info("MAIL - GET TEMPLATE'S TEXT");
        return new TemplateStorage().getTemplate(tempId);
    }
}
