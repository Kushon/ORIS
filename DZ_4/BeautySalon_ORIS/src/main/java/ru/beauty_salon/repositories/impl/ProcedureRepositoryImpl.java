
package ru.beauty_salon.repositories.impl;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.models.Procedure;
import ru.beauty_salon.repositories.ProcedureRepository;

public class ProcedureRepositoryImpl extends CrudRepositoryImpl<Procedure> implements ProcedureRepository {

    public ProcedureRepositoryImpl(ConnectionPool pool) {
        super(pool, Procedure::new);
        initializeStatements();
    }

    @Override
    protected void initializeStatements() {
        super.initializeStatements();
    }

}
