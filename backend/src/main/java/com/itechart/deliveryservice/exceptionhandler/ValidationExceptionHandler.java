package com.itechart.deliveryservice.exceptionhandler;

import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Component
public class ValidationExceptionHandler implements ExceptionMapper<ValidationException> {
    public static final int ERROR_CODE=422;

    @Override
    public Response toResponse(ValidationException e) {
        return Response.status(ERROR_CODE).entity(e.getMessage()).build();
    }
}
