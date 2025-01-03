package ru.beauty_salon.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

import ru.beauty_salon.models.Base;

public abstract interface CrudRepository<T extends Base> {

    abstract T builder(ResultSet resultSet) throws SQLException;

    abstract PreparedStatement prepareInsert(T object, Connection connection) throws SQLException;

    abstract PreparedStatement prepareUpdate(T object, Connection connection) throws SQLException;

    abstract PreparedStatement prepareDelete(T object, Connection connection) throws SQLException;

    abstract PreparedStatement prepareGetAll(Connection connection) throws SQLException;

    abstract PreparedStatement prepareGetById(Connection connection) throws SQLException;

    boolean create(T object);

    // ID ставится в методе АБСТРАКТНОГО класса
    Optional<T> getById(Long id);

    List<T> getAll();

    boolean update(T object);

    boolean delete(T object);

}
