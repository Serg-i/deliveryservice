package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.UserNameDTO;
import com.itechart.deliveryservice.controller.data.UsersForSelectDTO;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.User;
import com.itechart.deliveryservice.entity.UserRole;
import com.itechart.deliveryservice.utils.SearchParams;
import org.dozer.DozerBeanMapper;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/api/users")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ValidateRequest
public class UserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private DozerBeanMapper mapper;


    @GET
    @Path("/names")
    public UsersForSelectDTO getUsersForSelect() {

        SearchParams sp = new SearchParams();
        UsersForSelectDTO out = new UsersForSelectDTO();
        sp.addParam("role", UserRole.PROCESSING_MANAGER);
        List<User> first = userDao.searchAll(sp);
        sp = new SearchParams();
        sp.addParam("role", UserRole.COURIER);
        List<User> second = userDao.searchAll(sp);

        out.setCouriers(new ArrayList<UserNameDTO>());
        out.setProcissingManagers(new ArrayList<UserNameDTO>());

        for (User user : first) {
            out.getProcissingManagers().add(mapper.map(user, UserNameDTO.class));
        }
        for (User user : second) {
            out.getCouriers().add(mapper.map(user, UserNameDTO.class));
        }

        return out;
    }
}
