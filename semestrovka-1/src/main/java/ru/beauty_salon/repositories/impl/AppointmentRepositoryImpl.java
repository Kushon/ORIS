package ru.beauty_salon.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


import ru.beauty_salon.PooledConnection;
import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.enums.AppointmentStatus;
import ru.beauty_salon.models.Appointment;
import ru.beauty_salon.repositories.AppointmentRepository;

public class AppointmentRepositoryImpl extends CrudRepositoryImpl<Appointment> implements AppointmentRepository {

    private static final String INSERT = "insert into appointments (client, employee, procedure, appointment_date, status) values (?, ?, ?, ?, ?)";
    private static final String UPDATE = "update appointments set client = ?, employee = ?, procedure = ?, appointment_date = ?, status = ? where id = ?";
    private static final String DELETE = "delete from appointments where id = ?";
    private static final String GET_BY_ID = "select * from appointments where id = ?";
    private static final String GET_ALL = "select * from appointments";

    public AppointmentRepositoryImpl(ConnectionPool pool) {
        super(pool);
    }

    @Override
    public Appointment builder(ResultSet resultSet) throws SQLException {
        return Appointment.builder()
                .client(resultSet.getLong("client"))
                .employee(resultSet.getLong("employee"))
                .procedure(resultSet.getLong("procedure"))
                .appointmentDate(resultSet.getTimestamp("appointment_date"))
                .status((AppointmentStatus) resultSet.getObject("status"))
                .build();
    }

    @Override
    public PreparedStatement prepareInsert(Appointment object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

        statement.setObject(1, object.getClient(), Types.INTEGER);
        statement.setObject(2, object.getEmployee(), Types.INTEGER);
        statement.setObject(3, object.getProcedure(), Types.INTEGER);
        statement.setTimestamp(4, object.getAppointmentDate());
        statement.setObject(5, object.getStatus().name(), Types.OTHER);

        return statement;
    }

    @Override
    public PreparedStatement prepareUpdate(Appointment object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE);

        statement.setObject(1, object.getClient(), Types.INTEGER);
        statement.setObject(2, object.getEmployee(), Types.INTEGER);
        statement.setObject(3, object.getProcedure(), Types.INTEGER);
        statement.setTimestamp(4, object.getAppointmentDate());
        statement.setObject(5, object.getStatus().name(), Types.OTHER);

        statement.setObject(6, object.getId(), Types.INTEGER);

        return statement;
    }

    @Override
    public PreparedStatement prepareDelete(Appointment object, Connection connection) throws SQLException {
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
    public List<Appointment> getByClientId(Long id) {
        List<Appointment> result = new ArrayList<>();

        final String query = "select * from appointments where client = ?";

        try (PooledConnection pooledConnection = pool.getPooledConnection()) {
            Connection connection = pooledConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet= statement.executeQuery();

            while(resultSet.next()) 
                result.add(
                    builder(resultSet)
                );
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
