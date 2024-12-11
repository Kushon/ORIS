package ru.beauty_salon.repositories.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;

import java.sql.Types;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.RequiredArgsConstructor;

import ru.beauty_salon.repositories.CrudRepository;
import ru.beauty_salon.models.Base;
import ru.beauty_salon.ConnectionPool;

@RequiredArgsConstructor
public class CrudRepositoryImpl<T extends Base> implements CrudRepository<T> {

    private final ConnectionPool pool;
    private final Supplier<T> creator;

    private static String GET_BY_ID;
    private static String GET_ALL;
    private static String INSERT;
    private static String UPDATE_BY_ID;
    private static String DELETE_BY_ID;

    protected void initializeStatements() {
        Class<?> entityClass = getEntityClass();
        String tableName = getTableName(entityClass);
        List<String> columnNames = getColumnNames(entityClass);

        GET_BY_ID = String.format("SELECT * FROM %s WHERE id = ?", tableName);
        GET_ALL = String.format("SELECT * FROM %s", tableName);
        INSERT = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName,
                String.join(", ", columnNames),
                String.join(", ", Collections.nCopies(columnNames.size(), "?")));
        UPDATE_BY_ID = String.format("UPDATE %s SET %s WHERE id = ?",
                tableName,
                columnNames.stream().map(col -> col + " = ?").collect(Collectors.joining(", ")));
        DELETE_BY_ID = String.format("DELETE FROM %s WHERE id = ?", tableName);
    }

    private Class<?> getEntityClass() {
        return ((Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    private String getTableName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    private List<String> getColumnNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.getName().equals("id"))
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    /* START CRUD */

    @Override
    public boolean create(T entity) {
        try {
            Connection connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            setStatementParameters(statement, entity);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(1));
                    }
                }
            }

            pool.releaseConnection(connection);

            return rowsAffected > 0;
        } catch (SQLException | ReflectiveOperationException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<T> getById(Long id) {
        try {
            Connection connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
            statement.setLong(1, id);

            T type = creator.get();

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    populateObject(type, resultSet);
                }
            }

            pool.releaseConnection(connection);

            return type.getId() != Long.valueOf(-1) ? Optional.of(type) : Optional.empty();
        } catch (SQLException | ReflectiveOperationException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        try {
            Connection connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(GET_ALL);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    T entity = creator.get();
                    populateObject(entity, resultSet);
                    entities.add(entity);
                }
            }

            pool.releaseConnection(connection);

        } catch (SQLException | ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return entities;
    }

    @Override
    public boolean update(T entity) {
        try {
            Connection connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID);

            int parameterIndex = setStatementParameters(statement, entity);
            statement.setLong(parameterIndex, entity.getId());

            int rowsAffected = statement.executeUpdate();

            pool.releaseConnection(connection);

            return rowsAffected > 0;
        } catch (SQLException | ReflectiveOperationException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* END CRUD */

    /* REFLECTION AND UTILS START */

    // Заполнения типа результатами из запроса при помощи рефлексии
    private void populateObject(T object, ResultSet resultSet) throws SQLException, ReflectiveOperationException {
        // Рефлексируем
        Class<?> clazz = object.getClass();
        // Проходимся по всем методам в поисках сеттеров
        for (Method method : clazz.getMethods()) {
            if (isSetter(method)) {
                // Забираем название поля, ориентируясь на название сеттера
                String fieldName = getFieldNameFromSetter(method);
                // Забираем тип поля
                Class<?> paramType = method.getParameterTypes()[0];
                // Подготавливаем значение
                Object value = getValueFromResultSet(resultSet, fieldName, paramType);
                // Сеттим значение
                if (value != null) {
                    method.invoke(object, value);
                }
            }
        }
    }

    // Проверка метода на setter
    private boolean isSetter(Method method) {
        return Modifier.isPublic(method.getModifiers()) &&
                method.getName().startsWith("set") &&
                method.getParameterTypes().length == 1 &&
                method.getReturnType().equals(void.class);
    }

    // Проверка метода на getter
    private boolean isGetter(Method method) {
        return Modifier.isPublic(method.getModifiers()) &&
                (method.getName().startsWith("get") || method.getName().startsWith("is")) &&
                method.getParameterTypes().length == 0 &&
                !method.getReturnType().equals(void.class);
    }

    // Забирает название поля из названия сеттера
    private String getFieldNameFromSetter(Method setter) {
        String methodName = setter.getName();
        return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
    }

    // Создаем значение поля из ResultSet по названию и типу
    private Object getValueFromResultSet(ResultSet rs, String fieldName, Class<?> type) throws SQLException {
        if (type.equals(String.class))
            return rs.getString(fieldName);
        if (type.equals(Integer.class) || type.equals(int.class))
            return rs.getInt(fieldName);
        if (type.equals(Long.class) || type.equals(long.class))
            return rs.getLong(fieldName);
        if (type.equals(Double.class) || type.equals(double.class))
            return rs.getDouble(fieldName);
        if (type.equals(Boolean.class) || type.equals(boolean.class))
            return rs.getBoolean(fieldName);

        // TODO Больше рефлексии богу рефлексии
        return null;
    }

    private int setStatementParameters(PreparedStatement statement, T entity)
            throws SQLException, ReflectiveOperationException {
        Class<?> clazz = entity.getClass();
        int parameterIndex = 1;

        for (Method method : clazz.getMethods()) {
            if (isGetter(method) && !method.getName().equals("getId")) {
                Object value = method.invoke(entity);
                setStatementParameter(statement, parameterIndex++, value);
            }
        }

        return parameterIndex;
    }

    private void setStatementParameter(PreparedStatement statement, int index, Object value) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.NULL);
        } else if (value instanceof String) {
            statement.setString(index, (String) value);
        } else if (value instanceof Integer) {
            statement.setInt(index, (Integer) value);
        } else if (value instanceof Long) {
            statement.setLong(index, (Long) value);
        } else if (value instanceof Double) {
            statement.setDouble(index, (Double) value);
        } else if (value instanceof Boolean) {
            statement.setBoolean(index, (Boolean) value);
        }

        // TODO Больше типов богу типов
    }

    public boolean delete(T entity) {
        try {
            Connection connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, entity.getId());

            int rowsAffected = statement.executeUpdate();

            pool.releaseConnection(connection);

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* REFLECTION AND UTILS END */
}
