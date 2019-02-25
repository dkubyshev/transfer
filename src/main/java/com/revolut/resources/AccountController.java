package com.revolut.resources;

import com.revolut.exception.RequiredParamMissingException;
import com.revolut.response.ResponseBean;
import com.revolut.exception.InsufficientFundsException;
import com.revolut.service.AccountService;
import org.jooq.generated.tables.pojos.Account;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

/**
 * REST cotroller for money transfer between accounts
 */
@Path("/")
public class AccountController {

    @Inject
    private AccountService service;

    /**
     * Get account information
     * @param id - account id
     * @return
     */
    @GET
    @Path("/accounts/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account get(@PathParam("id") Long id){
        return service.get(id);
    }

    /**
     * Transfer money between account
     * @param idFrom - from id
     * @param idTo - to id
     * @param amount - transfer amount
     * @return
     */
    @POST
    @Path("/accounts/transfer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response transfer(@FormParam("idFrom") Long idFrom, @FormParam("idTo") Long idTo, @FormParam("amount") BigDecimal amount) throws InsufficientFundsException, RequiredParamMissingException {
        checkParam("idFrom", idFrom);
        checkParam("idTo", idTo);
        checkParam("amount", amount);

        service.transfer(idFrom, idTo, amount);
        return Response.ok(new ResponseBean(ResponseBean.Status.SUCCESS)).build();
    }

    private void checkParam(String paramName, Object paramVal) throws RequiredParamMissingException {
        if(paramVal == null){
            throw new RequiredParamMissingException(paramName);
        }
    }


}
