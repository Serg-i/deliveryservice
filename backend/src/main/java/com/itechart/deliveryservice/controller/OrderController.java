package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.OrderDTO;
import com.itechart.deliveryservice.controller.data.ShortOrderDTO;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.entity.Order;
import com.itechart.deliveryservice.entity.OrderHistory;
import org.codehaus.jackson.map.ObjectMapper;
import org.dozer.DozerBeanMapper;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/api/orders")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ValidateRequest
public class OrderController {

    private static final int OK_STATUS = 200;

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private DozerBeanMapper mapper;

    @GET
    @Transactional
    @Path("/{id}/history")
    public Response getHistory(@PathParam("id") long orderId) {
        Order order = orderDao.getById(orderId);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Response.status(OK_STATUS).entity(mapper.writeValueAsString(order.getHistory())).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("/")
    public List<ShortOrderDTO> readAll() {
        List<Order> orders = orderDao.getAll();
        List<ShortOrderDTO> list = new ArrayList<ShortOrderDTO>();
        for(Order order : orders)
            list.add(mapper.map(order, ShortOrderDTO.class));
        return list;
    }

    @GET
    @Path("/{id}")
    public OrderDTO readOne(@PathParam("id") long orderId) {
        Order order = orderDao.getById(orderId);
        return mapper.map(order, OrderDTO.class);
    }

    @POST
    @Path("/")
    public void create(@Valid OrderDTO orderDTO) {
        Order order =  mapper.map(orderDTO, Order.class);
        orderDao.save(order);
    }

    @PUT
    @Path("/{id}")
    public void update(@PathParam("id") long orderId, @Valid OrderDTO orderDTO) {
        Order order =  mapper.map(orderDTO, Order.class);
        order.setId(orderId);
        orderDao.merge(order);
    }

    @DELETE
    @Path("/{id}")
    public void update(@PathParam("id") long orderId) {
        Order order = orderDao.getById(orderId);
        orderDao.delete(order);
    }
}
