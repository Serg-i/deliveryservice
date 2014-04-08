package com.itechart.deliveryservice.exceptionhandler;


import org.springframework.dao.DataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

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
        BusinessLogicException exception=new BusinessLogicException();
        throw exception;
    }




    @GET
    @Path("/BusinessLogicException/{code}/{message}")
    public void throwBussinesLogicException(@PathParam("code") int code,@PathParam("message") String message)
            throws BusinessLogicException {
        BusinessLogicException e=new BusinessLogicException(message);
        e.setStatus(HttpStatus.valueOf(code));
        throw e;
    }



}
