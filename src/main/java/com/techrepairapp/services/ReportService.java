package com.techrepairapp.services;

import com.techrepairapp.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportService {

    // Method for getting stock statistics from spareparts table
    public String getSparePartsStockReport() {
        String sql = "SELECT MIN(quantity_in_stock) AS min_quantity, " +
                "MAX(quantity_in_stock) AS max_quantity, " +
                "AVG(quantity_in_stock) AS avg_quantity " +
                "FROM spareparts";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int minQuantity = rs.getInt("min_quantity");
                int maxQuantity = rs.getInt("max_quantity");
                double avgQuantity = rs.getDouble("avg_quantity");

                return String.format("Min: %d, Max: %d, Avg: %.2f", minQuantity, maxQuantity, avgQuantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Error fetching spare parts stock report.";
    }

    // Method for getting orders statistics
    public String getOrderStatusReport() {
        String sql = "SELECT status, COUNT(*) AS total_orders FROM orders GROUP BY status";
        StringBuilder report = new StringBuilder("Order Status Report:\n");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String status = rs.getString("status");
                int totalOrders = rs.getInt("total_orders");
                report.append(String.format("%s: %d orders\n", status, totalOrders));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report.toString();
    }

    // Method for getting parts below reorder level
    public String getPartsBelowReorderLevelReport() {
        String sql = "SELECT part_name, quantity_in_stock FROM spareparts WHERE quantity_in_stock < reorder_level";
        StringBuilder report = new StringBuilder("Parts Below Reorder Level:\n");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String partName = rs.getString("part_name");
                int quantityInStock = rs.getInt("quantity_in_stock");
                report.append(String.format("%s: %d in stock\n", partName, quantityInStock));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report.toString();
    }
}
