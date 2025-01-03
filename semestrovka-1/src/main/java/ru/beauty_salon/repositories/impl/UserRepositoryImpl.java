package ru.beauty_salon.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ru.beauty_salon.PooledConnection;
import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.dto.AppointmentDto;
import ru.beauty_salon.models.User;
import ru.beauty_salon.repositories.UserRepository;

public class UserRepositoryImpl extends CrudRepositoryImpl<User> implements UserRepository {

    private static final String INSERT = "insert into users (person, account, client, employee) values (?, ?, ?, ?)";
    private static final String UPDATE = "update users set person = ?, account = ?, client = ?, employee = ? where id = ?";
    private static final String DELETE = "delete from users where id = ?";
    private static final String GET_BY_ID = "select * from users where id = ?";
    private static final String GET_ALL = "select * from users";

    public UserRepositoryImpl(ConnectionPool pool) {
        super(pool);
    }

    @Override
    public User builder(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .person(resultSet.getLong("person"))
                .account(resultSet.getLong("account"))
                .client(resultSet.getLong("client"))
                .employee(resultSet.getLong("employee"))
                .build();
    }

    @Override
    public PreparedStatement prepareInsert(User object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

        statement.setObject(1, object.getPerson(), Types.INTEGER);
        statement.setObject(2, object.getAccount(), Types.INTEGER);
        statement.setObject(3, object.getClient(), Types.INTEGER);
        statement.setObject(4, object.getEmployee(), Types.INTEGER);

        return statement;
    }

    @Override
    public PreparedStatement prepareUpdate(User object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE);

        statement.setObject(1, object.getPerson(), Types.INTEGER);
        statement.setObject(2, object.getAccount(), Types.INTEGER);
        statement.setObject(3, object.getClient(), Types.INTEGER);
        statement.setObject(4, object.getEmployee(), Types.INTEGER);

        statement.setObject(5, object.getId(), Types.INTEGER);

        return statement;
    }

    @Override
    public PreparedStatement prepareDelete(User object, Connection connection) throws SQLException {
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
    public Optional<User> getByAccountId(Long id) {
        try (PooledConnection pooledConnection = pool.getPooledConnection()) {
            Connection connection = pooledConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement("select * from users where account = ?");
            statement.setObject(1, id, Types.INTEGER);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                return Optional.of(builder(resultSet));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<AppointmentDto> getActiveAppointments(Long id) {
        List<AppointmentDto> result = new ArrayList<>();

        try (PooledConnection pooledConnection = pool.getPooledConnection()) {
            final String query = //
                    "SELECT \n" + //
                            "    CONCAT(ep.first_name, ' ', ep.last_name) AS master,\n" + //
                            "    a.appointment_date AS date,\n" + //
                            "    pr.name AS service\n" + //
                            "FROM \n" + //
                            "    users u\n" + //
                            "JOIN \n" + //
                            "    clients c ON u.client = c.id\n" + //
                            "JOIN \n" + //
                            "    appointments a ON c.id = a.client\n" + //
                            "JOIN \n" + //
                            "    employees e ON a.employee = e.id\n" + //
                            "JOIN \n" + //
                            "    users eu ON e.id = eu.employee\n" + //
                            "JOIN \n" + //
                            "    persons ep ON eu.person = ep.id\n" + //
                            "JOIN \n" + //
                            "    procedures pr ON a.procedure = pr.id\n" + //
                            "WHERE \n" + //
                            "    u.id = ?\n" + //
                            "    AND a.status = 'CONFIRMED'\n" + //
                            "ORDER BY \n" + //
                            "    a.appointment_date DESC;";

            Connection connection = pooledConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, id, Types.INTEGER);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
                result.add(AppointmentDto.builder()
                        .date(resultSet.getTimestamp("date"))
                        .service(resultSet.getString("service"))
                        .master(resultSet.getString("master"))
                        .build());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<AppointmentDto> getAppointmentHistory(Long id) {
        List<AppointmentDto> result = new ArrayList<>();

        try (PooledConnection pooledConnection = pool.getPooledConnection()) {
            final String query = //
                    "SELECT \n" + //
                            "    CONCAT(ep.first_name, ' ', ep.last_name) AS master,\n" + //
                            "    a.appointment_date AS date,\n" + //
                            "    pr.name AS service\n" + //
                            "FROM \n" + //
                            "    users u\n" + //
                            "JOIN \n" + //
                            "    clients c ON u.client = c.id\n" + //
                            "JOIN \n" + //
                            "    appointments a ON c.id = a.client\n" + //
                            "JOIN \n" + //
                            "    employees e ON a.employee = e.id\n" + //
                            "JOIN \n" + //
                            "    users eu ON e.id = eu.employee\n" + //
                            "JOIN \n" + //
                            "    persons ep ON eu.person = ep.id\n" + //
                            "JOIN \n" + //
                            "    procedures pr ON a.procedure = pr.id\n" + //
                            "WHERE \n" + //
                            "    u.id = ?\n" + //
                            "    AND a.status = 'COMPLETED'\n" + //
                            "ORDER BY \n" + //
                            "    a.appointment_date DESC;";

            Connection connection = pooledConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, id, Types.INTEGER);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
                result.add(AppointmentDto.builder()
                        .date(resultSet.getTimestamp("date"))
                        .service(resultSet.getString("service"))
                        .master(resultSet.getString("master"))
                        .build());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
