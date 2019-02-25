package com.revolut.exception.mappers;

import com.revolut.response.ResponseBean;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {
  @Override
  @Produces(MediaType.APPLICATION_JSON)
  public Response toResponse(Throwable throwable) {
      return Response.serverError().entity(new ResponseBean(ResponseBean.Status.UNKNOWN, throwable.getMessage())).build();
  }
}
