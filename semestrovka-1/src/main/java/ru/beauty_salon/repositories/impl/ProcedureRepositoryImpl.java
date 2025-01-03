package ru.beauty_salon.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.models.Procedure;
import ru.beauty_salon.repositories.ProcedureRepository;

public class ProcedureRepositoryImpl extends CrudRepositoryImpl<Procedure> implements ProcedureRepository {

    private static final String INSERT = "insert into procedures (name, description, price, duration) values (?, ?, ?, ?)";
    private static final String UPDATE = "update procedures set name = ?, description = ?, price = ?, duration = ? where id = ?";
    private static final String DELETE = "delete from procedures where id = ?";
    private static final String GET_BY_ID = "select * from procedures where id = ?";
    private static final String GET_ALL = "select * from procedures";

    public ProcedureRepositoryImpl(ConnectionPool pool) {
        super(pool);
    }

    @Override
    public Procedure builder(ResultSet resultSet) throws SQLException {
        return Procedure.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .price(resultSet.getDouble("price"))
                .duration(resultSet.getInt("duration"))
                .build();
    }

    @Override
    public PreparedStatement prepareInsert(Procedure object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, object.getName());
        statement.setString(2, object.getDescription());
        statement.setDouble(3, object.getPrice());
        statement.setInt(4, object.getDuration());

        return statement;
    }

    @Override
    public PreparedStatement prepareUpdate(Procedure object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE);

        statement.setString(1, object.getName());
        statement.setString(2, object.getDescription());
        statement.setDouble(3, object.getPrice());
        statement.setInt(4, object.getDuration());

        statement.setObject(5, object.getId(), Types.INTEGER);

        return statement;
    }

    @Override
    public PreparedStatement prepareDelete(Procedure object, Connection connection) throws SQLException {
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
