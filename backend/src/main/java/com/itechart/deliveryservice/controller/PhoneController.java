package com.itechart.deliveryservice.controller;
import com.itechart.deliveryservice.controller.data.PhoneDTO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.PhoneDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.entity.Phone;
import com.itechart.deliveryservice.exceptionhandler.BusinessLogicException;
import org.dozer.DozerBeanMapper;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

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

    private static final Logger logger = LoggerFactory.getLogger(PhoneController.class);

    @GET
    @Path("/{contactId}/phones")
    public List<PhoneDTO> getAllPhones(@PathParam("contactId") long contactId) throws Exception {

        logger.info("PHONE - READ ALL");

        Contact owner = contactDao.getById(contactId);
        if (owner == null)
            throw new BusinessLogicException("This contact doesn't exist", HttpStatus.NOT_FOUND);
        List<PhoneDTO> list = new ArrayList<PhoneDTO>();
        for(Phone p : owner.getPhones())
            list.add(mapper.map(p, PhoneDTO.class));
        return list;
    }

    @POST
    @Path("/{contactId}/phones/")
    public void getPhone(@PathParam("contactId") long contactId, @Valid PhoneDTO phoneDTO) throws Exception {

        logger.info("PHONE - CREATE");

        Phone phone =  mapper.map(phoneDTO, Phone.class);
        Contact owner = contactDao.getById(contactId);
        if (owner == null)
            throw new BusinessLogicException("This contact doesn't exist", HttpStatus.NOT_FOUND);
        phone.setOwner(owner);
        phoneDao.save(phone);
    }

    @PUT
    @Path("/{contactId}/phones/{phoneId}")
    public void getPhone(@PathParam("contactId") long contactId,
                                   @PathParam("phoneId") long phoneId, @Valid PhoneDTO phoneDTO) throws Exception {

        logger.info("PHONE - UPDATE");

        Phone phone = phoneDao.getById(phoneId);
        if (phone == null)
            throw new BusinessLogicException("This phone doesn't exist", HttpStatus.NOT_FOUND);
        phone.setNumber(phoneDTO.getNumber());
        phone.setCountryCode(phoneDTO.getCountryCode());
        phone.setOperatorCode(phoneDTO.getOperatorCode());
        phone.setComment(phoneDTO.getComment());
        phone.setType(phoneDTO.getType());
        phoneDao.merge(phone);
    }

    @DELETE
    @Path("/{contactId}/phones/{phoneId}")
    public void deletePhone(@PathParam("phoneId") long phoneId) throws Exception {

        logger.info("PHONE - DELETE");

        Phone phone = phoneDao.getById(phoneId);
        if (phone == null)
            throw new BusinessLogicException("This phone doesn't exist", HttpStatus.NOT_FOUND);
        phoneDao.delete(phone);
    }
}
