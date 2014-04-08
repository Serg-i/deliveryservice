package com.itechart.deliveryservice.exceptionhandler;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExceptionHandlersIntegrationTest {

    private Dispatcher dispatcher;

    @Before
    public void init() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(new ThrowableController());
        dispatcher.getProviderFactory().registerProvider(RuntimeExceptionHandler.class);
        dispatcher.getProviderFactory().registerProvider(ExceptionHandler.class);
        dispatcher.getProviderFactory().registerProvider(DataAccessExceptionHandler.class);
        dispatcher.getProviderFactory().registerProvider(BusinessLogicExceptionHandler.class);
    }

    @After
    public final void stop() {
    }

    @Test
    public void shouldHandleRuntimeExceptionsWithOtherProvidersCorrectly() throws  Exception{
        MockHttpRequest request = MockHttpRequest.get("/test/RuntimeException");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(RuntimeExceptionHandler.ERROR_CODE, response.getStatus());
        assertEquals(RuntimeExceptionHandler.ERROR_MESSAGE,response.getContentAsString());
    }

    @Test
    public void shouldHandleExceptionsWithOtherProvidersCorrectly() throws  Exception{
        MockHttpRequest request = MockHttpRequest.get("/test/Exception");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(ExceptionHandler.ERROR_CODE, response.getStatus());
        assertEquals(ExceptionHandler.ERROR_MESSAGE,response.getContentAsString());
    }

    @Test
    public void shouldHandleDataAccessExceptionWithOtherProvidersCorrectly() throws Exception{
        MockHttpRequest request = MockHttpRequest.get("/test/DataAccessException");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(DataAccessExceptionHandler.ERROR_CODE, response.getStatus());
        assertEquals(DataAccessExceptionHandler.DATA_ACCESS_ERROR_MESSAGE,response.getContentAsString());
    }

    @Test
    public void shouldHandleBusinessLogicExceptionWithOtherProvidersCorrectly() throws Exception{
        MockHttpRequest request = MockHttpRequest.get("/test/BusinessLogicException");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(BusinessLogicExceptionHandler.DEFAULT_ERROR_CODE, response.getStatus());
        assertEquals(BusinessLogicExceptionHandler.DEFAULT_ERROR_MESSAGE,response.getContentAsString());
    }
}
