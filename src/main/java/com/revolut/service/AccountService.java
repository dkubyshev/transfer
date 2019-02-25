package com.revolut.service;

import com.revolut.exception.InsufficientFundsException;
import org.jooq.generated.tables.pojos.Account;
import org.jvnet.hk2.annotations.Contract;

import java.math.BigDecimal;

/**
 * Account service
 */
@Contract
public interface AccountService {

    /**
     * Get account info
     * @param id - account id
     * @return
     */
    Account get(Long id);

    /**
     * Money transfer between accounts
     * @param idFrom - from id
     * @param idTo - to id
     * @param amount - transfer amount
     * @throws InsufficientFundsException
     */
    void transfer(Long idFrom, Long idTo, BigDecimal amount) throws InsufficientFundsException;
}
