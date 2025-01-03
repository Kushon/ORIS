package ru.beauty_salon.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import java.sql.Types;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.PooledConnection;

import ru.beauty_salon.models.Account;
import ru.beauty_salon.repositories.AccountRepository;

public class AccountRepositoryImpl extends CrudRepositoryImpl<Account> implements AccountRepository {

    public AccountRepositoryImpl(ConnectionPool pool) {
        super(pool);
    }

    private static final String INSERT = "insert into accounts (email, hash) values (?, ?)";
    private static final String UPDATE = "update accounts set email = ?, hash = ? where id = ?";
    private static final String DELETE = "delete from accounts where id = ?";
    private static final String GET_BY_ID = "select * from accounts where id = ?";
    private static final String GET_ALL = "select * from accounts";

    @Override
    public Account builder(ResultSet resultSet) throws SQLException {
        return Account.builder()
                .id(resultSet.getLong("id"))
                .email(resultSet.getString("email"))
                .hash(resultSet.getString("hash"))
                .isAdmin(resultSet.getBoolean("is_admin"))
                .build();
    }

    @Override
    public PreparedStatement prepareInsert(Account object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, object.getEmail());
        statement.setString(2, object.getHash());

        return statement;
    }

    @Override
    public PreparedStatement prepareUpdate(Account object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE);

        statement.setString(1, object.getEmail());
        statement.setString(2, object.getHash());

        statement.setObject(3, object.getId(), Types.INTEGER);

        return statement;
    }

    @Override
    public PreparedStatement prepareDelete(Account object, Connection connection) throws SQLException {
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

    @Override
    public Optional<Account> getByEmail(String email) {
        try (PooledConnection pooledConnection = pool.getPooledConnection()) {
            Connection connection = pooledConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement("select * from accounts where email = ?");
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                return Optional.of(builder(resultSet));

            pool.releaseConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
