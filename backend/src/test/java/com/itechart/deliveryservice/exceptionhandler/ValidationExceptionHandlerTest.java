package com.itechart.deliveryservice.exceptionhandler;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidationExceptionHandlerTest {

    private Dispatcher dispatcher;

    @Before
    public void init() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(new ThrowableController());
        dispatcher.getProviderFactory().registerProvider(ValidationExceptionHandler.class);
    }

    @After
    public final void stop() {
    }


    @Test
    public void shouldHandleValidationException() throws Exception{
        MockHttpRequest request = MockHttpRequest.get("/test/ValidationException");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(ValidationExceptionHandler.ERROR_CODE, response.getStatus());
    }

}
