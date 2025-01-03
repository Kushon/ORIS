package ru.beauty_salon.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import ru.beauty_salon.PooledConnection;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.models.Base;
import ru.beauty_salon.repositories.CrudRepository;

@AllArgsConstructor
public abstract class CrudRepositoryImpl<T extends Base> implements CrudRepository<T> {
    protected ConnectionPool pool;

    public abstract T builder(ResultSet resultSet) throws SQLException;

    public boolean create(T object) {
        try (PooledConnection pooledConnection = pool.getPooledConnection()) {
            Connection connection = pooledConnection.getConnection();
            PreparedStatement statement = prepareInsert(object, connection);

            int rows = statement.executeUpdate();
            if (rows >= 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                object.setId(resultSet.getLong(1));

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<T> getById(Long id) {
        try (PooledConnection pooledConnection = pool.getPooledConnection()) {
            Connection connection = pooledConnection.getConnection();
            PreparedStatement statement = prepareGetById(connection);
            statement.setObject(1, id, Types.INTEGER);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                return Optional.of(builder(resultSet));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<T> getAll() {
        List<T> objects = new ArrayList<>();
        try (PooledConnection pooledConnection = pool.getPooledConnection()) {
            Connection connection = pooledConnection.getConnection();
            PreparedStatement statement = prepareGetAll(connection);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                objects.add(builder(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    public boolean update(T object) {
        try (PooledConnection pooledConnection = pool.getPooledConnection()) {
            Connection connection = pooledConnection.getConnection();
            PreparedStatement statement = prepareUpdate(object, connection);

            int rows = statement.executeUpdate();
            if (rows >= 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(T object) {
        try (PooledConnection pooledConnection = pool.getPooledConnection()) {
            Connection connection = pooledConnection.getConnection();
            PreparedStatement statement = prepareDelete(object, connection);

            int rows = statement.executeUpdate();
            if (rows >= 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
