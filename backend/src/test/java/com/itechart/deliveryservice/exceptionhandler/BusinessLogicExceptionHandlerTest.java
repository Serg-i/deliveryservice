package com.itechart.deliveryservice.exceptionhandler;


import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BusinessLogicExceptionHandlerTest {

    private Dispatcher dispatcher;

    @Before
    public void init() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(new ThrowableController());
        dispatcher.getProviderFactory().registerProvider(BusinessLogicExceptionHandler.class);
    }

    @After
    public final void stop() {
    }

    @Test
    public void shouldHandleBusinessLogicExceptionAndSetDefaultDataToResponse() throws Exception{
        MockHttpRequest request = MockHttpRequest.get("/test/BusinessLogicException");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(BusinessLogicException.DEFAULT_ERROR_STATUS.value(), response.getStatus());
        assertEquals(BusinessLogicException.DEFAULT_ERROR_MESSAGE,response.getContentAsString());
    }

    @Test
    public void shouldHandleBusinessLogicExceptionAndSetExceptionDataToResponse() throws Exception{
        int code=501;
        String message="error";
        MockHttpRequest request = MockHttpRequest.get("/test/BusinessLogicException/"+code+"/"+message);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(code, response.getStatus());
        assertEquals(message,response.getContentAsString());
    }
}
