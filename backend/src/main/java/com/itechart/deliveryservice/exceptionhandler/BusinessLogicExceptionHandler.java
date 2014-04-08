package com.itechart.deliveryservice.exceptionhandler;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Component
public class BusinessLogicExceptionHandler implements ExceptionMapper<BusinessLogicException> {

    public static final int DEFAULT_ERROR_CODE=500;
    public static final String DEFAULT_ERROR_MESSAGE = "Internal Server Error";

    @Override
    public Response toResponse(BusinessLogicException businessLogicExeption) {
        HttpStatus status= HttpStatus.valueOf(DEFAULT_ERROR_CODE) ;
        if(businessLogicExeption.getStatus()!=null){
            status=businessLogicExeption.getStatus();
        }
        String message= DEFAULT_ERROR_MESSAGE;
        if(businessLogicExeption.getMessage()!=null){
            message=businessLogicExeption.getMessage();
        }
        return Response.status(status.value()).entity(message).build();
    }
}
