package com.revolut.exception.mappers;

import com.revolut.exception.RequiredParamMissingException;
import com.revolut.response.ResponseBean;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Required param missing exception mapper
 */
@Provider
public class RequiredParamMissingExceptionMapper implements ExceptionMapper<RequiredParamMissingException> {
    @Override
    public Response toResponse(RequiredParamMissingException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseBean(ResponseBean.Status.BAD_REQUEST, exception.getMessage())).build();
    }
}
