package com.techrepairapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/techrepair";
    private static final String USER = "postgres"; // Update with your PostgreSQL username
    private static final String PASSWORD = "yash123"; // Update with your PostgreSQL password

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new SQLException("PostgreSQL JDBC Driver not found.");
            }
        }
        return connection;
    }
}
