
package ru.beauty_salon.repositories.impl;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.models.Client;
import ru.beauty_salon.repositories.ClientRepository;

public class ClientRepositoryImpl extends CrudRepositoryImpl<Client> implements ClientRepository {

    public ClientRepositoryImpl(ConnectionPool pool) {
        super(pool, Client::new);
        initializeStatements();
    }

    @Override
    protected void initializeStatements() {
        super.initializeStatements();
    }

}
