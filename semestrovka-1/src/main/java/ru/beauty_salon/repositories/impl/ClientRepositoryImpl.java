package ru.beauty_salon.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.models.Client;
import ru.beauty_salon.repositories.ClientRepository;

public class ClientRepositoryImpl extends CrudRepositoryImpl<Client> implements ClientRepository {

    private static final String INSERT = "insert into clients (fav_procedure) values (?)";
    private static final String UPDATE = "update clients set fav_procedure = ? where id = ?";
    private static final String DELETE = "delete from clients where id = ?";
    private static final String GET_BY_ID = "select * from clients where id = ?";
    private static final String GET_ALL = "select * from clients";

    public ClientRepositoryImpl(ConnectionPool pool) {
        super(pool);
    }

    @Override
    public Client builder(ResultSet resultSet) throws SQLException {
        return Client.builder()
                .id(resultSet.getLong("id"))
                .favProcedure(resultSet.getLong("fav_procedure"))
                .build();
    }

    @Override
    public PreparedStatement prepareInsert(Client object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

        statement.setObject(1, object.getFavProcedure(), Types.BIGINT);

        return statement;
    }

    @Override
    public PreparedStatement prepareUpdate(Client object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE);

        statement.setObject(1, object.getFavProcedure(), Types.INTEGER);

        statement.setObject(2, object.getId(), Types.INTEGER);

        return statement;
    }

    @Override
    public PreparedStatement prepareDelete(Client object, Connection connection) throws SQLException {
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
