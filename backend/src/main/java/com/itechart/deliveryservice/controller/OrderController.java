package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.entity.Order;
import com.itechart.deliveryservice.entity.OrderHistory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/order")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

    private static final int OK_STATUS = 200;

    @Autowired
    private OrderDao orderDao;

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
}
