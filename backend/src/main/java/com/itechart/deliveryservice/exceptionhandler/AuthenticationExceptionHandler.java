package com.itechart.deliveryservice.exceptionhandler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Component
public class AuthenticationExceptionHandler implements ExceptionMapper<AuthenticationException> {

    public static final int ERROR_CODE=401;

    @Override
    public Response toResponse(AuthenticationException e) {
        return Response.status(ERROR_CODE).entity(e.getMessage()).build();
    }
}
