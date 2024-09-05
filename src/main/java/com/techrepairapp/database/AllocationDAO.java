package com.techrepairapp.database;

import com.techrepairapp.models.Employee;
import com.techrepairapp.models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AllocationDAO {
    private Connection connection;

    public AllocationDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public void allocate(Employee employee, Order order) {
        String sql = "INSERT INTO allocations (employeeId, orderId) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employee.getId());
            stmt.setInt(2, order.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    // Add more methods if needed
}
