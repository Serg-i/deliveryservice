package com.itechart.deliveryservice.exceptionhandler;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Component
public class DataAccessExceptionHandler implements ExceptionMapper<DataAccessException> {

    public static final int ERROR_CODE=500;
    public static final String DATA_ACCESS_ERROR_MESSAGE="Data Access error";

    @Override
    public Response toResponse(DataAccessException e) {
        return Response.status(ERROR_CODE).entity(DATA_ACCESS_ERROR_MESSAGE).build();
    }
}