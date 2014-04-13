package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.OrderChangeDTO;
import com.itechart.deliveryservice.controller.data.OrderDTO;
import com.itechart.deliveryservice.controller.data.ShortOrderDTO;
import com.itechart.deliveryservice.controller.data.TableDTO;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.*;
import com.itechart.deliveryservice.exceptionhandler.BadRequestException;
import com.itechart.deliveryservice.utils.SearchParams;
import com.itechart.deliveryservice.utils.Settings;
import org.dozer.DozerBeanMapper;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
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
    @Path("/p/{page}")
    public TableDTO<ShortOrderDTO> readAll(@PathParam("page") int page) {

        List<Order> orders = null;
        User user = getUser();
        SearchParams sp = new SearchParams();
        int count = 0;
        switch (user.getRole()) {
            case PROCESSING_MANAGER:
                sp.addParam("processingManager.id", Long.toString(user.getId()));
                sp.addParam("state", OrderState.ACCEPTED);
                sp.addParam("state", OrderState.IN_PROCESSING);
                break;
            case COURIER:
                sp.addParam("deliveryManager.id", Long.toString(user.getId()));
                sp.addParam("state", OrderState.READY_FOR_DELIVERY);
                sp.addParam("state", OrderState.DELIVERY);
                break;
            default:
        }
        count = (int)orderDao.searchCount(sp);
        int from = Math.min((page - 1) * Settings.rows, Math.max(0, count - Settings.rows));
        orders = orderDao.search(sp, from, Settings.rows, "date", false);
        TableDTO<ShortOrderDTO> out = new TableDTO<ShortOrderDTO>();
        List<ShortOrderDTO> list = new ArrayList<ShortOrderDTO>();
        for(Order order : orders)
            list.add(mapper.map(order, ShortOrderDTO.class));
        out.setCurrentPage(list);
        out.setCount(count);
        out.setPagesCount(count / Settings.rows + (count % Settings.rows == 0 ? 0 : 1) );
        return out;
    }

    @GET
    @Path("/{id}")
    public OrderDTO readOne(@PathParam("id") long orderId) throws Exception {

        Order order = orderDao.getById(orderId);
        if (order == null)
            throw new BadRequestException();
        if (!canAccessOrder(order))
            throw new AccessDeniedException("User is not allowed to see that order");
        return mapper.map(order, OrderDTO.class);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_ORDER_MANAGER"})
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

    @Secured("ROLE_ADMINISTRATOR")
    @DELETE
    @Path("/{id}")
    public void update(@PathParam("id") long orderId) {
        Order order = orderDao.getById(orderId);
        orderDao.delete(order);
    }

    private boolean canAccessOrder(Order order) {

        boolean can = false;
        User user = getUser();
        if (user.getRole() == UserRole.SUPERVISOR
                || user.getRole() == UserRole.ORDER_MANAGER
                || user.getRole() == UserRole.ADMINISTRATOR)
            return true;
        can |= order.getProcessingManager() == user
                && (order.getState() == OrderState.ACCEPTED
                    || order.getState() == OrderState.IN_PROCESSING);
        can |= order.getDeliveryManager() == user
                && (order.getState() == OrderState.READY_FOR_DELIVERY
                || order.getState() == OrderState.DELIVERY);
        return can;
    }

    private User getUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userDao.getByName(name);
        return user;
    }
}
