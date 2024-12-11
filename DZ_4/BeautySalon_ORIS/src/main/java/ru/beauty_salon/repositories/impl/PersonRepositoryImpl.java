
package ru.beauty_salon.repositories.impl;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.models.Person;
import ru.beauty_salon.repositories.PersonRepository;

public class PersonRepositoryImpl extends CrudRepositoryImpl<Person> implements PersonRepository {

    public PersonRepositoryImpl(ConnectionPool pool) {
        super(pool, Person::new);
        initializeStatements();
    }

    @Override
    protected void initializeStatements() {
        super.initializeStatements();
    }

}
