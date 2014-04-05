package com.itechart.deliveryservice.controller;
import com.itechart.deliveryservice.controller.data.ContactDTO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.entity.*;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.dozer.DozerBeanMapper;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Path("/api/contacts")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ValidateRequest
public class ContactController {

    @Autowired
    private ContactDao contactDao;
    @Autowired
    private DozerBeanMapper mapper;

    @GET
    @Path("/{idContact}")
    public ContactDTO getContact(@PathParam("idContact") long idContact) {
       Contact contact = contactDao.getById(idContact);
       return mapper.map(contact, ContactDTO.class);
    }

    @GET
    public List<ContactDTO> getContacts(){
        List<ContactDTO> contacts = new ArrayList<ContactDTO>();
        for (Contact contact: contactDao.getAll()){
            contacts.add(mapper.map(contact, ContactDTO.class));
        }
        return contacts;
    }

    @POST
    public void insertContact(@Valid ContactDTO contactDTO){
          Contact contact = mapper.map(contactDTO, Contact.class);
          contactDao.save(contact);
    }

    @PUT
    @Path("/{idContact}")
    public void updateContact(@PathParam("idContact") long idContact, @Valid ContactDTO contactDTO){
        Contact contact = mapper.map(contactDTO, Contact.class);
        contact.setId(idContact);
        contactDao.merge(contact);
    }

    @DELETE
    @PathParam("/{idContact}")
    public void deleteContact(@PathParam("idContact") long idContact){
        Contact contact = contactDao.getById(idContact);
        contactDao.delete(contact);
    }
}