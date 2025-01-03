package ru.beauty_salon.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.models.Person;
import ru.beauty_salon.repositories.PersonRepository;

public class PersonRepositoryImpl extends CrudRepositoryImpl<Person> implements PersonRepository {

    private static final String INSERT = "insert into persons (first_name, last_name) values (?, ?)";
    private static final String UPDATE = "update persons set first_name = ?, last_name = ? where id = ?";
    private static final String DELETE = "delete from persons where id = ?";
    private static final String GET_BY_ID = "select * from persons where id = ?";
    private static final String GET_ALL = "select * from persons";

    public PersonRepositoryImpl(ConnectionPool pool) {
        super(pool);
    }

    @Override
    public Person builder(ResultSet resultSet) throws SQLException {
        return Person.builder()
                .id(resultSet.getLong(1))
                .firstName(resultSet.getString(2))
                .lastName(resultSet.getString(3))
                .build();
    }

    @Override
    public PreparedStatement prepareInsert(Person object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, object.getFirstName());
        statement.setString(2, object.getLastName());

        return statement;
    }

    @Override
    public PreparedStatement prepareUpdate(Person object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE);

        statement.setString(1, object.getFirstName());
        statement.setString(2, object.getLastName());

        statement.setObject(3, object.getId(), Types.INTEGER);

        return statement;
    }

    @Override
    public PreparedStatement prepareDelete(Person object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE);

        statement.setObject(1, object.getId(), Types.INTEGER);

        return statement;
    }

    @Override
    public PreparedStatement prepareGetAll(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_ALL);

        return statement;
    }

    @Override
    public PreparedStatement prepareGetById(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_BY_ID);

        return statement;
    }
}
