package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.OrderChangeDTO;
import com.itechart.deliveryservice.controller.data.OrderDTO;
import com.itechart.deliveryservice.controller.data.ShortOrderDTO;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.entity.Order;
import com.itechart.deliveryservice.entity.OrderChange;
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
    @Path("/{id}/orderChanges")
    public List<OrderChangeDTO> getChanges(@PathParam("id") long orderId) {
        Order order = orderDao.getById(orderId);
        List<OrderChangeDTO> orderChanges = new ArrayList<OrderChangeDTO>(order.getChanges().size());
        for(OrderChange orderChange : order.getChanges()) {
            orderChanges.add(mapper.map(orderChange, OrderChangeDTO.class));
        }
        return orderChanges;
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
