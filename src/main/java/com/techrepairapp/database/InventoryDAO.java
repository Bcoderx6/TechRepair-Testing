package com.techrepairapp.database;

import com.techrepairapp.models.InventoryItem;
import com.techrepairapp.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    public void addPart(InventoryItem item) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("INSERT INTO SpareParts (part_name, quantity_in_stock, reorder_level) VALUES (?, ?, ?)")) {
            pstmt.setString(1, item.getPartName());
            pstmt.setInt(2, item.getQuantity());
            pstmt.setInt(3, item.getReorderLevel());
            pstmt.executeUpdate();
        }
    }

    public void updatePart(InventoryItem item) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("UPDATE SpareParts SET part_name = ?, quantity_in_stock = ?, reorder_level = ? WHERE part_id = ?")) {
            pstmt.setString(1, item.getPartName());
            pstmt.setInt(2, item.getQuantity());
            pstmt.setInt(3, item.getReorderLevel());
            pstmt.setInt(4, item.getPartId());
            pstmt.executeUpdate();
        }
    }

    public void removePart(int partId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("DELETE FROM SpareParts WHERE part_id = ?")) {
            pstmt.setInt(1, partId);
            pstmt.executeUpdate();
        }
    }

    public List<InventoryItem> getLowStockItems() throws SQLException {
        List<InventoryItem> items = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM SpareParts WHERE quantity_in_stock <= reorder_level");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int partId = rs.getInt("part_id");
                String partName = rs.getString("part_name");
                int quantity = rs.getInt("quantity_in_stock");
                int reorderLevel = rs.getInt("reorder_level");
                Date lastOrderDate = rs.getDate("last_order_date");
                items.add(new InventoryItem(partId, partName, quantity, reorderLevel, lastOrderDate));
            }
        }
        return items;
    }

    public int getCurrentStock(int partId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT quantity_in_stock FROM SpareParts WHERE part_id = ?")) {
            pstmt.setInt(1, partId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity_in_stock");
            }
            return 0;
        }
    }

    public int getReorderLevel(int partId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT reorder_level FROM SpareParts WHERE part_id = ?")) {
            pstmt.setInt(1, partId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("reorder_level");
            }
            return 0;
        }
    }

    public List<InventoryItem> getAllInventoryItems() throws SQLException {
        List<InventoryItem> items = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SpareParts")) {
            while (resultSet.next()) {
                InventoryItem item = new InventoryItem(
                        resultSet.getInt("part_id"),
                        resultSet.getString("part_name"),
                        resultSet.getInt("quantity_in_stock"),
                        resultSet.getInt("reorder_level"),
                        resultSet.getDate("last_order_date")
                );
                items.add(item);
            }
        }
        return items;
    }
}
