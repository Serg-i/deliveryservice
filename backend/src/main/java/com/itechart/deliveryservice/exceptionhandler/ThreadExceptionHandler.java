package com.itechart.deliveryservice.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.ws.rs.ext.Provider;

@Provider
@Component
public class ThreadExceptionHandler  implements Thread.UncaughtExceptionHandler  {

    @Override
    public void uncaughtException(Thread t, Throwable e){
        try {
            throw new BusinessLogicException("message", e);
        } catch (BusinessLogicException e1) {
            e1.printStackTrace();
        }
    }
}
