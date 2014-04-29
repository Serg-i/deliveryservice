package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.*;
import com.itechart.deliveryservice.dao.OrderChangeDao;
import com.itechart.deliveryservice.dao.OrderDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.*;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.itechart.deliveryservice.utils.Utils.firstItem;

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
    private OrderChangeDao orderChangeDao;
    @Autowired
    private DozerBeanMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GET
    @Path("/p/{page}")
    public TableDTO<ShortOrderDTO> readAll(@PathParam("page") int page) throws Exception {

        logger.info("ORDER - READ ALL");

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
        orders = orderDao.search(sp, firstItem(page, count), Settings.getRows(), "date", false);
        TableDTO<ShortOrderDTO> out = new TableDTO<ShortOrderDTO>();
        List<ShortOrderDTO> list = new ArrayList<ShortOrderDTO>();
        for(Order order : orders)
            list.add(mapper.map(order, ShortOrderDTO.class));
        out.setCurrentPage(list);
        out.setCount(count);
        return out;
    }

    @GET
    @Path("/{id}")
    public OrderDTO readOne(@PathParam("id") long orderId) throws Exception {

        logger.info("ORDER - READ ONE");

        Order order = orderDao.getById(orderId);
        if (order == null)   {
            logger.error("ORDER - NOT FOUND");
            throw new BusinessLogicException("This order doesn't exist", HttpStatus.NOT_FOUND);
        }
        if (!canAccessOrder(order)) {
            logger.error("ORDER - ACCESS DENIED");
            throw new BusinessLogicException("Access Denied", HttpStatus.FORBIDDEN);
        }
        return mapper.map(order, OrderDTO.class);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_ORDER_MANAGER"})
    @POST
    @Path("/")
    public void create(@Valid ReceiveOrderDTO orderDTO) throws Exception {

        logger.info("ORDER - CREATE");

        Order order =  mapper.map(orderDTO, Order.class);
        order.setState(OrderState.NEW);
        order.setReceptionManager(getUser());
        order.setDate(new Date());
        orderDao.save(order);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_ORDER_MANAGER", "ROLE_SUPERVISOR"})
    @PUT
    @Path("/{id}")
    public void update(@PathParam("id") long orderId,
                       @Valid ReceiveOrderDTO orderDTO) throws Exception {

        logger.info("ORDER - UPDATE");

        Order upOrder =  mapper.map(orderDTO, Order.class);
        Order order = orderDao.getById(orderId);
        if (order == null){
            logger.error("ORDER - NOT FOUND");
            throw new BusinessLogicException("This order doesn't exist", HttpStatus.NOT_FOUND);
        }
        upOrder.setDate(order.getDate());
        upOrder.setReceptionManager(order.getReceptionManager());
        upOrder.setState(order.getState());
        upOrder.setChanges(order.getChanges());
        orderDao.merge(upOrder);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long orderId) {

        logger.info("ORDER - DELETE");
        Order order = orderDao.getById(orderId);
        orderDao.delete(order);
    }

    @PUT
    @Path("/{id}/state")
    public void updateState(@PathParam("id") long orderId,
                             @Valid NewStateDTO state) throws Exception {

        logger.info("ORDER - UPDATE STATE");

        OrderState st = state.getNewState();
        Order order = orderDao.getById(orderId);
        if (order == null){
            logger.error("ORDER - NOT FOUND");
            throw new BusinessLogicException("This order doesn't exist", HttpStatus.NOT_FOUND);
        }
        if (!canAccessOrder(order) || !canSetOrderState(order, st)){
            logger.error("ORDER - ACCESS DENIED");
            throw new BusinessLogicException("Access Denied", HttpStatus.FORBIDDEN);
        }
        OrderChange oc = new OrderChange();
        oc.setComment(state.getComment());
        oc.setUserChangedStatus(getUser());
        oc.setOrder(order);
        oc.setDate(new Date());
        oc.setNewState(st);

        order.setState(st);
        orderDao.merge(order);
    }

    @POST
    @Path("/search/p/{page}")
    public TableDTO<ShortOrderDTO> searchOrders(@PathParam("page") int page, @Valid SearchOrderDTO dto) {

        logger.info("ORDER - SEARCH");

        SearchParams sp = dto.createParams();
        User user = getUser();
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
        int count = (int)orderDao.searchCount(sp);
        TableDTO<ShortOrderDTO> table = new TableDTO<ShortOrderDTO>();
        table.setCount((int)count);
        List<Order> found = orderDao.search(sp, firstItem(page, count), Settings.getRows());
        List<ShortOrderDTO> list = new ArrayList<ShortOrderDTO>();
        for(Order p : found)
            list.add(mapper.map(p, ShortOrderDTO.class));
        table.setCurrentPage(list);
        return table;
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

    private boolean canSetOrderState(Order order, OrderState state) {

        User user = getUser();
        UserRole role = user.getRole();
        OrderState cur = order.getState();
        if (cur == OrderState.CANCELED
                || cur == OrderState.CLOSED)
            return false;
        if (role == UserRole.ADMINISTRATOR)
            return true;
        switch (state) {
            case CAN_NOT_BE_EXECUTED:
                return true;
            case CANCELED:
                if (role == UserRole.ORDER_MANAGER || role == UserRole.SUPERVISOR)
                    return true;
                break;
            case ACCEPTED:
                if (cur == OrderState.NEW && role == UserRole.ORDER_MANAGER)
                    return true;
                break;
            case IN_PROCESSING:
                if (cur == OrderState.ACCEPTED && order.getProcessingManager() == user)
                    return true;
                break;
            case READY_FOR_DELIVERY:
                if (cur == OrderState.IN_PROCESSING && order.getProcessingManager() == user)
                    return true;
                break;
            case DELIVERY:
                if (cur == OrderState.READY_FOR_DELIVERY && order.getDeliveryManager() == user)
                    return true;
                break;
            case CLOSED:
                if (cur == OrderState.DELIVERY && order.getDeliveryManager() == user)
                    return true;
                break;
            case NEW:
                if (cur == OrderState.CAN_NOT_BE_EXECUTED
                        && role == UserRole.ORDER_MANAGER || role == UserRole.SUPERVISOR)
                    return true;
                break;
        }
        return false;
    }

    private User getUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userDao.getByName(name);
        return user;
    }
}
