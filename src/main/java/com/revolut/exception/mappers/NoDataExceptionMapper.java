package com.revolut.exception.mappers;

import com.revolut.response.ResponseBean;
import org.jooq.exception.NoDataFoundException;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoDataExceptionMapper implements ExceptionMapper<NoDataFoundException> {
  @Override
  @Produces(MediaType.APPLICATION_JSON)
  public Response toResponse(NoDataFoundException exception) {
    return Response.status(Response.Status.NOT_FOUND).entity(new ResponseBean(ResponseBean.Status.NOT_FOUND, exception.getMessage())).build();
  }
}
