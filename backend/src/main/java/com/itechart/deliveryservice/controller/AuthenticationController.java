package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.LoginInfoDTO;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.User;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/login")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class AuthenticationController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private DozerBeanMapper mapper;

    @GET
    public LoginInfoDTO login() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userDao.getByName(name);
        return mapper.map(user, LoginInfoDTO.class);
    }
}
