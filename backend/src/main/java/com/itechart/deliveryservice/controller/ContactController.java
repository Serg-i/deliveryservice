package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.*;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.entity.Order;
import com.itechart.deliveryservice.entity.OrderState;
import com.itechart.deliveryservice.entity.User;
import com.itechart.deliveryservice.exceptionhandler.BusinessLogicException;
import com.itechart.deliveryservice.utils.SearchParams;
import com.itechart.deliveryservice.utils.Settings;
import org.dozer.DozerBeanMapper;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static com.itechart.deliveryservice.utils.Utils.firstItem;

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
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private DozerBeanMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @GET
    @Path("/{idContact}")
    public ContactDTO readOne(@PathParam("idContact") long idContact) throws Exception{

       logger.info("CONTACT - READ ONE");
       Contact contact = contactDao.getById(idContact);
        if (contact == null)
            throw new BusinessLogicException("This contact doesn't exist", HttpStatus.NOT_FOUND);
       return mapper.map(contact, ContactDTO.class);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_ORDER_MANAGER", "ROLE_SUPERVISOR"})
    @GET
    @Path("/p/{page}")
    public TableDTO<ShortContactDTO> readAll(@PathParam("page") int page) throws Exception{

        logger.info("CONTACT - READ ALL");

        int count = (int) contactDao.getCount();
        List<Contact> contacts = contactDao.getOffset(firstItem(page, count), Settings.getRows());
        TableDTO<ShortContactDTO> out = new TableDTO<ShortContactDTO>();
        List<ShortContactDTO> list = new ArrayList<ShortContactDTO>();
        for(Contact contact: contacts)
            list.add(mapper.map(contact, ShortContactDTO.class));
        out.setCurrentPage(list);
        out.setCount(count);
        return out;
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_ORDER_MANAGER", "ROLE_SUPERVISOR"})
    @POST
    public ContactDTO insert(@Valid ContactDTO contactDTO){

        logger.info("CONTACT - INSERT");
        Contact contact = mapper.map(contactDTO, Contact.class);
        contactDao.save(contact);
        return mapper.map(contact, ContactDTO.class);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_ORDER_MANAGER", "ROLE_SUPERVISOR"})
    @PUT
    @Path("/{idContact}")
    public void update(@PathParam("idContact") long idContact, @Valid ContactDTO contactDTO){

        logger.info("CONTACT - UPDATE");
        Contact contact = mapper.map(contactDTO, Contact.class);
        contact.setId(idContact);
        contactDao.merge(contact);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_ORDER_MANAGER", "ROLE_SUPERVISOR"})
    @DELETE
    @Path("/{idContact}")
    public void delete(@PathParam("idContact") long idContact) throws Exception{

        logger.info("CONTACT - DELETE");
        Contact contact = contactDao.getById(idContact);
        List<User> users = userDao.getAll();
        List<Order> orders = orderDao.getAll();

        for (User user: users){
            if (user.getContact().equals(contact)){
                throw new BusinessLogicException("Deletion forbidden, user contains this contact", HttpStatus.METHOD_NOT_ALLOWED);
            }
        }
        for (Order order: orders){
                if (order.getState()!= OrderState.CANCELED
                        && order.getState()!= OrderState.CLOSED
                        &&
                        (order.getCustomer().equals(contact)
                            || order.getRecipient().equals(contact)
                            || order.getDeliveryManager().getContact().equals(contact)
                            || order.getProcessingManager().getContact().equals(contact)
                            || order.getReceptionManager().getContact().equals(contact))){
                        throw new BusinessLogicException("Deletion forbidden, order contains this contact", HttpStatus.METHOD_NOT_ALLOWED);
                }  else contactDao.delete(contact);
        }
    }

    @GET
    @Path("/names")
    public List<ContactNameDTO> getAllNames() {

        logger.info("CONTACT - GET ALL NAMES");

        List<Contact> contacts = contactDao.getAll();
        List<ContactNameDTO> out = new ArrayList<ContactNameDTO>();
        for (Contact contact: contacts){
            out.add(mapper.map(contact, ContactNameDTO.class));
        }
        return out;
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_ORDER_MANAGER", "ROLE_SUPERVISOR"})
    @POST
    @Path("/search/p/{page}")
    public TableDTO<ContactDTO>  searchContacts(@PathParam("page") int page, @Valid SearchContactDTO dto) {

        logger.info("CONTACT - SEARCH");

        SearchParams sp = dto.createParams();
        int count = (int) contactDao.searchCount(sp);
        TableDTO<ContactDTO> table = new TableDTO<ContactDTO>();
        table.setCount(count);
        List<Contact> found = contactDao.search(sp, firstItem(page, count), Settings.getRows());
        List<ContactDTO> list = new ArrayList<ContactDTO>();
        for(Contact contact : found)
            list.add(mapper.map(contact, ContactDTO.class));
        table.setCurrentPage(list);
        return table;
    }
}
