package com.itechart.deliveryservice.exceptionhandler;


import org.springframework.dao.DataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;

import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/test")
@Controller
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ThrowableController {

    @GET
    @Path("/Exception")
    public void throwException() throws Exception {
        throw new Exception();
    }

    @GET
    @Path("/RuntimeException")
    public void  throwRuntimeException(){
          throw new RuntimeException();
    }

    @GET
    @Path("/DataAccessException")
    public void throwDataAccessException() throws DataAccessException {
        throw new RecoverableDataAccessException("");
    }

    @GET
    @Path("/BusinessLogicException")
    public void throwBusinessLogicException()
            throws BusinessLogicException {
        throw new BusinessLogicException();
    }


    @GET
    @Path("/BusinessLogicException/{code}/{message}")
    public void throwBusinessLogicException(@PathParam("code") int code,@PathParam("message") String message)
            throws BusinessLogicException {
        BusinessLogicException e=new BusinessLogicException(message);
        e.setStatus(HttpStatus.valueOf(code));
        throw e;
    }

    @GET
    @Path("/ValidationException")
    public void throwValidationException(){
        throw new ValidationException();
    }

    @GET
    @Path("/AuthenticationException")
    public void throwAuthenticationException(){
        throw new AuthenticationCredentialsNotFoundException("");
    }



}
