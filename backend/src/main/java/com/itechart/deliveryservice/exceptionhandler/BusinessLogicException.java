package com.itechart.deliveryservice.exceptionhandler;

import org.springframework.http.HttpStatus;

public class BusinessLogicException extends Exception{

    public static final String DEFAULT_ERROR_MESSAGE = "Internal Server Error";
    public static final HttpStatus DEFAULT_ERROR_STATUS =HttpStatus.INTERNAL_SERVER_ERROR;

    private HttpStatus status= DEFAULT_ERROR_STATUS;

    public BusinessLogicException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

    public BusinessLogicException(String message) {
        super(message);
    }

    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessLogicException(Throwable cause) {
        super(DEFAULT_ERROR_MESSAGE,cause);
    }

    public BusinessLogicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
