package com.itechart.deliveryservice.controller;

import com.itechart.deliveryservice.controller.data.*;
import com.itechart.deliveryservice.dao.ContactDao;
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
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.List;

import static com.itechart.deliveryservice.utils.Utils.firstItem;

@Path("/api/users")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ValidateRequest
public class UserController {

    public static final String CAN_NOT_DELETE_MESSAGE = "User process orders , can not delete";

    @Autowired
    private UserDao userDao;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private OrderDao orderDao;
    @Qualifier(value = "mapper")
    @Autowired
    private DozerBeanMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);


    @GET
    @Path("/names")
    public UsersForSelectDTO getUsersForSelect() {

        logger.info("USER - GET USERS FOR SELECT");

        SearchParams sp = new SearchParams();
        UsersForSelectDTO out = new UsersForSelectDTO();
        sp.addParam("role", UserRole.PROCESSING_MANAGER);
        List<User> first = userDao.searchAll(sp);
        sp = new SearchParams();
        sp.addParam("role", UserRole.COURIER);
        List<User> second = userDao.searchAll(sp);

        out.setCouriers(new ArrayList<UserNameDTO>());
        out.setProcessingManagers(new ArrayList<UserNameDTO>());

        for (User user : first) {
            out.getProcessingManagers().add(mapper.map(user, UserNameDTO.class));
        }
        for (User user : second) {
            out.getCouriers().add(mapper.map(user, UserNameDTO.class));
        }

        return out;
    }

    @GET
    @Secured({"ROLE_ADMINISTRATOR"})
    @Path("/{id}")
    public UserDTO getUser(@PathParam("id") long id) throws Exception {

        logger.info("USER - READ ONE");
        User user = userDao.getById(id);
        if (user == null) {
            logger.error("USER - NOT FOUND");
            throw new BusinessLogicException("This user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return mapper.map(user, UserDTO.class);
    }

    @GET
    @Secured({"ROLE_ADMINISTRATOR"})
    @Path("/p/{page}")
    public TableDTO<UserViewDTO> getUsersPage(@PathParam("page") int page) {

        logger.info("USER - READ ALL");
        int count = (int) userDao.getCount();
        List<User> users = userDao.getOffset(firstItem(page, count), Settings.getRows());
        TableDTO<UserViewDTO> out = new TableDTO<UserViewDTO>();
        List<UserViewDTO> result = new ArrayList<UserViewDTO>();
        for (User user : users) {
            result.add(mapper.map(user, UserViewDTO.class));
        }
        out.setCurrentPage(result);
        out.setCount(count);
        return out;
    }

    @POST
    @Secured({"ROLE_ADMINISTRATOR"})
    @Path("/")
    public void createUser(@Valid UserCreateDTO userCreateDTO) throws Exception {

        logger.info("USER - CREATE");
        User user = mapper.map(userCreateDTO, User.class);
        Contact contact = contactDao.getById(userCreateDTO.getContactId());
        if (contact == null) {
            logger.error("USER - CONTACT NOT FOUND");
            throw new BusinessLogicException("This contact doesn't exist", HttpStatus.NOT_FOUND);
        }
        user.setContact(contact);
        userDao.save(user);
    }


    @PUT
    @Secured({"ROLE_ADMINISTRATOR"})
    @Path("/{id}")
    public void updateUser(UserModifyDTO userModifyDTO,@PathParam("id")long id) throws Exception {

        logger.info("USER - UPDATE");
        User user = mapper.map(userModifyDTO, User.class);
        User userToModify=userDao.getById(id);
        checkIfThisUserAuthorized(userToModify);
        Contact contact = contactDao.getById(userModifyDTO.getContactId());
        if (contact == null) {
            logger.error("USER - CONTACT NOT FOUND");
            throw new BusinessLogicException("This contact doesn't exist", HttpStatus.NOT_FOUND);
        }
        user.setContact(contact);
        userDao.merge(user);
    }

    @DELETE
    @Secured({"ROLE_ADMINISTRATOR"})
    @Path("/{id}")
    public void deleteUser(@PathParam("id") long id) throws BusinessLogicException {

        logger.info("USER - DELETE");
        User user = userDao.getById(id);
        List<Order> orders = orderDao.getAll();
        checkIfThisUserAuthorized(user);
        for (Order order : orders) {
            if ((order.getState() != OrderState.CLOSED && order.getState() != OrderState.CANCELED)
                    &&
                    (order.getReceptionManager().equals(user)
                            || order.getProcessingManager().equals(user)
                            || order.getDeliveryManager().equals(user)))
                throw new BusinessLogicException(CAN_NOT_DELETE_MESSAGE, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        userDao.delete(user);
    }

    private void checkIfThisUserAuthorized(User user) throws BusinessLogicException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User currentLoginUser = userDao.getByName(name);
        if(currentLoginUser.equals(user)){
            logger.error("USER - CAN NOT MODIFY HIMSELF");
            throw new BusinessLogicException("",HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


}
