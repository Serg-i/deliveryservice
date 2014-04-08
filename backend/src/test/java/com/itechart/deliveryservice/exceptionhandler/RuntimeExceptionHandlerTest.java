package com.itechart.deliveryservice.exceptionhandler;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RuntimeExceptionHandlerTest {

    private Dispatcher dispatcher;

    @Before
    public void init() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(new ThrowableController());
        dispatcher.getProviderFactory().registerProvider(RuntimeExceptionHandler.class);
    }

    @After
    public final void stop() {
    }

    @Test
    public void shouldHandleRuntimeException() throws Exception{
        MockHttpRequest request = MockHttpRequest.get("/test/RuntimeException");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(RuntimeExceptionHandler.ERROR_CODE, response.getStatus());
        assertEquals(RuntimeExceptionHandler.ERROR_MESSAGE,response.getContentAsString());
    }
}
