package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.SearchContactDTO;
import com.itechart.deliveryservice.controller.data.ShortContactDTO;
import com.itechart.deliveryservice.controller.data.TableDTO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.utils.SearchParams;
import com.itechart.deliveryservice.utils.Settings;
import org.dozer.DozerBeanMapper;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/api/search")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ValidateRequest
public class SearchController {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private DozerBeanMapper mapper;


    @GET
    @Path("/contacts/{page}")
    public TableDTO<ShortContactDTO> searchContacts(@PathParam("page") long page, @Valid SearchContactDTO dto) {

        SearchParams sp = dto.createParams();
        long count = contactDao.searchCount(sp);
        TableDTO<ShortContactDTO> table = new TableDTO<ShortContactDTO>();
        table.setCount((int)count);
        List<Contact> found = contactDao.search(sp,
                (int)(page-1) * Settings.getRows(), Settings.getRows());
        List<ShortContactDTO> list = new ArrayList<ShortContactDTO>();
        for(Contact p : found)
            list.add(mapper.map(p, ShortContactDTO.class));
        table.setCurrentPage(list);
        return table;
    }

}
