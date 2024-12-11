
package ru.beauty_salon.repositories.impl;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.models.Payment;
import ru.beauty_salon.repositories.PaymentRepository;

public class PaymentRepositoryImpl extends CrudRepositoryImpl<Payment> implements PaymentRepository {

    public PaymentRepositoryImpl(ConnectionPool pool) {
        super(pool, Payment::new);
        initializeStatements();
    }

    @Override
    protected void initializeStatements() {
        super.initializeStatements();
    }

}
