package com.itechart.deliveryservice.controller;
import java.util.ArrayList;
import java.util.List;

import com.itechart.deliveryservice.controller.data.PhoneDTO;
import org.dozer.DozerBeanMapper;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.PhoneDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.entity.Phone;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/contacts")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ValidateRequest
public class PhoneController {

    @Autowired
    private PhoneDao phoneDao;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private DozerBeanMapper mapper;

    @GET
    @Path("/{contactId}/phones")
    public List<PhoneDTO> getAllPhones(@PathParam("contactId") long contactId) {

        Contact owner = contactDao.getById(contactId);
        List<PhoneDTO> list = new ArrayList<PhoneDTO>();
        for(Phone p : owner.getPhones())
            list.add(mapper.map(p, PhoneDTO.class));
        return list;
    }

    @PUT
    @Path("/{contactId}/phones/{phoneId}")
    public void getPhone(@PathParam("contactId") long contactId,
                                   @PathParam("phoneId") long phoneId, @Valid PhoneDTO phoneDTO) {

        Phone phone =  mapper.map(phoneDTO, Phone.class);
        Contact owner = contactDao.getById(contactId);
        phone.setId(phoneId);
        phone.setOwner(owner);
        phoneDao.merge(phone);
    }

    @DELETE
    @Path("/{contactId}/phones/{phoneId}")
    public void deletePhone(@PathParam("phoneId") long phoneId) {

        Phone phone = phoneDao.getById(phoneId);
        phoneDao.delete(phone);
    }
}
