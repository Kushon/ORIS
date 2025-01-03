package ru.beauty_salon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
        }
    }

    private static final int INITIAL_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final String URL = "jdbc:postgresql://localhost:5432/sem";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";

    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();

    public ConnectionPool() throws SQLException {
        connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionPool.add(createConnection());
        }
    }

    private static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public PooledConnection getPooledConnection() throws SQLException {
        Connection connection = getConnection();
        return new PooledConnection(connection, this);
    }

    private Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE + 1) {
                connectionPool.add(createConnection());
            } else {
                throw new SQLException("Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }
}
