package com.itechart.deliveryservice.exceptionhandler;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Component
public class BusinessLogicExceptionHandler implements ExceptionMapper<BusinessLogicException> {


    @Override
    public Response toResponse(BusinessLogicException businessLogicExeption) {
        HttpStatus status=businessLogicExeption.getStatus();
        String message=businessLogicExeption.getMessage();
        return Response.status(status.value()).entity(message).build();
    }
}
