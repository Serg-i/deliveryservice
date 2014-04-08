package com.itechart.deliveryservice.exceptionhandler;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Component
public class ExceptionHandler implements ExceptionMapper<Exception> {

    public static final int ERROR_CODE=500;
    public static final String ERROR_MESSAGE="Server runtime error";

    @Override
    public Response toResponse(Exception e) {
        return Response.status(ERROR_CODE).entity(ERROR_MESSAGE).build();
    }
}
