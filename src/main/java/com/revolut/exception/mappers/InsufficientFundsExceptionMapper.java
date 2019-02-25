package com.revolut.exception.mappers;

import com.revolut.response.ResponseBean;
import com.revolut.exception.InsufficientFundsException;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Insufficient funds exception mapper
 */
@Provider
public class InsufficientFundsExceptionMapper implements ExceptionMapper<InsufficientFundsException> {

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(InsufficientFundsException exception) {
        return Response.status(Response.Status.PAYMENT_REQUIRED).entity(new ResponseBean(ResponseBean.Status.INSUFFICIENT_FUNDS, exception.getMessage())).build();
    }
}
