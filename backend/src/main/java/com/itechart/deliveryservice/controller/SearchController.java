package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.CountOfRowsDTO;
import com.itechart.deliveryservice.controller.data.SearchOrderDTO;
import com.itechart.deliveryservice.controller.data.ShortOrderDTO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.entity.Order;
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
    @Path("/orders/count")
    public CountOfRowsDTO getCount(@Valid SearchOrderDTO dto) {

        SearchParams sp = dto.createParams();
        CountOfRowsDTO c = new CountOfRowsDTO();
        c.setCount((int)orderDao.searchCount(sp));
        return c;
    }

    @GET
    @Path("/orders/{page}")
    public List<ShortOrderDTO> getChanges(@PathParam("page") long page, @Valid SearchOrderDTO dto) {

        SearchParams sp = dto.createParams();
        List<Order> found = orderDao.search(sp, (int)(page-1)* Settings.rows, Settings.rows);
        List<ShortOrderDTO> list = new ArrayList<ShortOrderDTO>();
        for(Order p : found)
            list.add(mapper.map(p, ShortOrderDTO.class));
        return list;
    }
}
