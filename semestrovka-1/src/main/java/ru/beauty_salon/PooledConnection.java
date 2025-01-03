package ru.beauty_salon;

import java.sql.Connection;

public class PooledConnection implements AutoCloseable {
    private Connection connection;
    private ConnectionPool pool;

    public PooledConnection(Connection connection, ConnectionPool pool) {
        this.connection = connection;
        this.pool = pool;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        pool.releaseConnection(connection);
    }
}
