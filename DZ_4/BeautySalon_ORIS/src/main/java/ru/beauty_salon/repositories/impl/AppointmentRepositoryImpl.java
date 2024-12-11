
package ru.beauty_salon.repositories.impl;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.models.Appointment;
import ru.beauty_salon.repositories.AppointmentRepository;

public class AppointmentRepositoryImpl extends CrudRepositoryImpl<Appointment> implements AppointmentRepository {

    public AppointmentRepositoryImpl(ConnectionPool pool) {
        super(pool, Appointment::new);
        initializeStatements();
    }

    @Override
    protected void initializeStatements() {
        super.initializeStatements();
    }

}
