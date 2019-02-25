package com.revolut.repo;

import org.jooq.generated.tables.pojos.Account;
import org.jvnet.hk2.annotations.Contract;

/**
 * Account repository
 */
@Contract
public interface AccountRepository {

    Account get(Long id);

    Account update(Account account);
}
