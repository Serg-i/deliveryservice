package com.itechart.deliveryservice.exceptionhandler;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataAccessExceptionHandlerTest {

    private Dispatcher dispatcher;

    @Before
    public void init() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(new ThrowableController());
        dispatcher.getProviderFactory().registerProvider(DataAccessExceptionHandler.class);
    }

    @After
    public final void stop() {
    }

    @Test
    public void shouldHandleDataAccessException() throws Exception{
        MockHttpRequest request = MockHttpRequest.get("/test/DataAccessException");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(DataAccessExceptionHandler.ERROR_CODE, response.getStatus());
        assertEquals(DataAccessExceptionHandler.DATA_ACCESS_ERROR_MESSAGE,response.getContentAsString());
    }
}
