
package ru.beauty_salon.repositories.impl;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.models.Employee;
import ru.beauty_salon.repositories.EmployeeRepository;

public class EmployeeRepositoryImpl extends CrudRepositoryImpl<Employee> implements EmployeeRepository {

    public EmployeeRepositoryImpl(ConnectionPool pool) {
        super(pool, Employee::new);
        initializeStatements();
    }

    @Override
    protected void initializeStatements() {
        super.initializeStatements();
    }

}
