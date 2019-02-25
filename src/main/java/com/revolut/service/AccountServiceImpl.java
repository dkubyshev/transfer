package com.revolut.service;

import com.google.common.util.concurrent.Striped;
import com.revolut.exception.InsufficientFundsException;
import com.revolut.repo.AccountRepository;
import org.jooq.generated.tables.pojos.Account;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;

/**
 * Account service implementation
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    private final static Striped<Lock> lock = Striped.lock(2);

    @Inject
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    /**
     * Get account info
     * @param id - account id
     * @return
     */
    @Override
    public Account get(Long id) {
        return repository.get(id);
    }

    /**
     * Money transfer between accounts
     * @param idFrom - from id
     * @param idTo - to id
     * @param amount - transfer amount
     * @throws InsufficientFundsException
     */
    @Override
    public void transfer(Long idFrom, Long idTo, BigDecimal amount) throws InsufficientFundsException {
        final Lock first, second;
        if(idFrom < idTo){
            first = lock.get(idFrom);
            second = lock.get(idTo);
        } else {
            first = lock.get(idTo);
            second = lock.get(idFrom);
        }

        first.lock();
        try {
            second.lock();
            try {
                Account fromAccount = repository.get(idFrom);
                BigDecimal fromBalance = fromAccount.getBalance();

                Account toAccount = repository.get(idTo);
                BigDecimal toBalance = toAccount.getBalance();

                if(fromBalance.compareTo(amount) >= 0){
                    fromAccount.setBalance(fromBalance.subtract(amount));
                    toAccount.setBalance(toBalance.add(amount));

                    repository.update(fromAccount);
                    repository.update(toAccount);
                } else {
                    throw new InsufficientFundsException("Insufficient Funds");
                }
            } finally {
                second.unlock();
            }
        } finally {
            first.unlock();
        }
    }
}
