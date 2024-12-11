
package ru.beauty_salon.repositories.impl;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.models.User;
import ru.beauty_salon.repositories.UserRepository;

public class UserRepositoryImpl extends CrudRepositoryImpl<User> implements UserRepository {

    public UserRepositoryImpl(ConnectionPool pool) {
        super(pool, User::new);
        initializeStatements();
    }

    @Override
    protected void initializeStatements() {
        super.initializeStatements();
    }

}
