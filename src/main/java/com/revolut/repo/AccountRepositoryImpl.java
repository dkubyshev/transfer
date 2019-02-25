package com.revolut.repo;


import com.revolut.ds.Ds;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.generated.tables.pojos.Account;
import org.jooq.generated.tables.records.AccountRecord;
import org.jooq.impl.DSL;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;

import static org.jooq.generated.Tables.ACCOUNT;

/**
 * Account repository implementation
 */
@Service
public class AccountRepositoryImpl implements AccountRepository {

    private final DSLContext ctx;

    private final Ds ds;

    @Inject
    public AccountRepositoryImpl(Ds ds) {
        this.ds = ds;
        this.ctx = DSL.using(this.ds.getDataSource(), SQLDialect.H2);
    }

    @Override
    public Account get(Long id) {
        AccountRecord recordById = getRecordById(id);
        return recordById.into(Account.class);
    }

    @Override
    public Account update(Account account) {
        AccountRecord accountRecord = getRecordById(account.getId());
        accountRecord.from(account);
        accountRecord.store();
        return accountRecord.into(Account.class);
    }

    private AccountRecord getRecordById(Long id) {
        return ctx.selectFrom(ACCOUNT).where(ACCOUNT.ID.eq(id)).fetchSingle();
    }
}
