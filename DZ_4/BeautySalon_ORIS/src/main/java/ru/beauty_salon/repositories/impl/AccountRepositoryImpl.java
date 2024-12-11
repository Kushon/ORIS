
package ru.beauty_salon.repositories.impl;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.models.Account;
import ru.beauty_salon.repositories.AccountRepository;

public class AccountRepositoryImpl extends CrudRepositoryImpl<Account> implements AccountRepository {

    public AccountRepositoryImpl(ConnectionPool pool) {
        super(pool, Account::new);
        initializeStatements();
    }

    @Override
    protected void initializeStatements() {
        super.initializeStatements();
    }

}
