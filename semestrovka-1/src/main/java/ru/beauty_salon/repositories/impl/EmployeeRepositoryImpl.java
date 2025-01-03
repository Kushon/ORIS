package ru.beauty_salon.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.PooledConnection;
import ru.beauty_salon.dto.EmployeeDto;
import ru.beauty_salon.enums.EmployeePosition;
import ru.beauty_salon.models.Employee;
import ru.beauty_salon.repositories.EmployeeRepository;

public class EmployeeRepositoryImpl extends CrudRepositoryImpl<Employee> implements EmployeeRepository {

    private static final String INSERT = "insert into employees (position, experience, is_active, hire_date, termination_date) values (?, ?, ?, ?, ?)";
    private static final String UPDATE = "update employees set position = ?, experience = ?, is_active = ?, hire_date = ?, termination_date = ? where id = ?";
    private static final String DELETE = "delete from employees where id = ?";
    private static final String GET_BY_ID = "select * from employees where id = ?";
    private static final String GET_ALL = "select * from employees";

    public EmployeeRepositoryImpl(ConnectionPool pool) {
        super(pool);
    }

    @Override
    public Employee builder(ResultSet resultSet) throws SQLException {
        return Employee.builder()
                .id(resultSet.getLong("id"))
                .position(EmployeePosition.valueOf(resultSet.getString("position")))
                .experience(resultSet.getInt("experience"))
                .hireDate(resultSet.getDate("hire_date"))
                .terminationDate(resultSet.getDate("termination_date"))
                .isActive(resultSet.getBoolean("is_active"))
                .build();
    }

    @Override
    public PreparedStatement prepareInsert(Employee object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, object.getPosition().name());
        statement.setDouble(2, object.getExperience());
        statement.setBoolean(3, object.isActive());
        statement.setDate(4, object.getHireDate());
        statement.setDate(5, object.getTerminationDate());

        return statement;
    }

    @Override
    public PreparedStatement prepareUpdate(Employee object, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE);

        statement.setString(1, object.getPosition().name());
        statement.setDouble(2, object.getExperience());
        statement.setBoolean(3, object.isActive());
        statement.setDate(4, object.getHireDate());
        statement.setDate(5, object.getTerminationDate());

        statement.setObject(6, object.getId(), Types.INTEGER);

        return statement;
    }

    @Override
    public PreparedStatement prepareDelete(Employee object, Connection connection) throws SQLException {
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
    public List<EmployeeDto> getAllByProcedureId(Long id) {
        List<EmployeeDto> result = new ArrayList<>();

        try (PooledConnection pooledConnection = pool.getPooledConnection()) {
            final String query = //
                    "SELECT DISTINCT\n" + //
                            "    e.id AS id,\n" + //
                            "    p.first_name || ' ' || p.last_name AS name\n" + //
                            "FROM \n" + //
                            "    employees e\n" + //
                            "JOIN \n" + //
                            "    employee_procedures ep ON e.id = ep.employee_id\n" + //
                            "JOIN \n" + //
                            "    users u ON e.id = u.employee\n" + //
                            "JOIN \n" + //
                            "    persons p ON u.person = p.id\n" + //
                            "WHERE \n" + //
                            "    ep.procedure_id = ?\n" + //
                            "    AND e.is_active = TRUE\n" + //
                            "ORDER BY \n" + //
                            "    e.id;\n";

            Connection connection = pooledConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, id, Types.INTEGER);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
                result.add(EmployeeDto.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .build());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
