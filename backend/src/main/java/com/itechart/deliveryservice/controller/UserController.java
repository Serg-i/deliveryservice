package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.*;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.entity.User;
import com.itechart.deliveryservice.entity.UserRole;
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

import static com.itechart.deliveryservice.utils.Utils.firstItem;

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
    private ContactDao contactDao;
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

    @GET
    @Path("/{id}")
    public UserDTO getUser(@PathParam("id") long id){
        User user=userDao.getById(id);
        return mapper.map(user,UserDTO.class);
    }

    @GET
    @Path("/p/{page}")
    public TableDTO<UserDTO> getUsersPage(@PathParam("page")int page){
        int count=(int)userDao.getCount();
        List<User> users = userDao.getOffset(firstItem(page, count), Settings.getRows());
        TableDTO<UserDTO> out = new TableDTO<UserDTO>();
        List<UserDTO> result=new ArrayList<UserDTO>();
        for(User user:users){
            result.add(mapper.map(user,UserDTO.class));
        }
        out.setCurrentPage(result);
        out.setCount(count);
        return out;
    }

    @POST
    @Path("/")
    public void createUser(@Valid UserCreateDTO userCreateDTO){
        User user=mapper.map(userCreateDTO,User.class);
        Contact contact=contactDao.getById(userCreateDTO.getContactId());
        user.setContact(contact);
        userDao.save(user);
    }


    @PUT
    @Path("/{id}")
    public void updateUser(UserUpdateDTO userUpdateDTO){
        User user=mapper.map(userUpdateDTO,User.class);
        Contact contact=contactDao.getById(userUpdateDTO.getContactId());
        user.setContact(contact);
        userDao.merge(user);
    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") long id){
        User user=userDao.getById(id);
        userDao.delete(user);
    }
}
