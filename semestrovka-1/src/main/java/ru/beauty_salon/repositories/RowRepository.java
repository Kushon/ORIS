package ru.beauty_salon.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.PooledConnection;

@RequiredArgsConstructor
public class RowRepository {

    private final ConnectionPool pool;

    public List<List<String>> getRows(String tableName) {
        String GET_ALL = "select * from " + tableName;

        List<List<String>> rows = new ArrayList<>();

        try (PooledConnection pooledConnection = pool.getPooledConnection()) {
            Connection connection = pooledConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(GET_ALL);

            ResultSet resultSet = statement.executeQuery();

            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    String value = resultSet.getString(i);
                    row.add(value != null ? value : "NULL");
                }

                rows.add(row);
            }

            pool.releaseConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows;
    }

    public List<String> getColumns(String tableName) {
        List<String> columns = new ArrayList<>();

        try (PooledConnection pooledConnection = pool.getPooledConnection()) {
            Connection connection = pooledConnection.getConnection();
            
            String query = "select column_name from information_schema.columns where table_name = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, tableName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String columnName = resultSet.getString("column_name");
                columns.add(columnName);
            }

            pool.releaseConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return columns;
    }
}
