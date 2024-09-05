package com.techrepairapp.services;

import com.techrepairapp.database.InventoryDAO;
import com.techrepairapp.models.InventoryItem;
import java.sql.SQLException;
import java.util.List;

public class InventoryService {
    private final InventoryDAO inventoryDAO;

    public InventoryService() {
        this.inventoryDAO = new InventoryDAO();
    }

    public void addPart(InventoryItem item) throws SQLException {
        inventoryDAO.addPart(item);
    }

    public void updatePart(InventoryItem item) throws SQLException {
        inventoryDAO.updatePart(item);
    }

    public void removePart(int partId) throws SQLException {
        inventoryDAO.removePart(partId);
    }

    public List<InventoryItem> getLowStockItems() throws SQLException {
        return inventoryDAO.getLowStockItems();
    }

    public void purchasePart(int partId, int quantityPurchased) throws SQLException {
        int currentStock = inventoryDAO.getCurrentStock(partId);
        int newQuantity = currentStock + quantityPurchased;
        inventoryDAO.updatePart(new InventoryItem(partId, null, newQuantity, 0, null)); // Dummy values for simplicity

        if (newQuantity <= inventoryDAO.getReorderLevel(partId)) {
            createOrder(partId, newQuantity);
        }
    }

    private void createOrder(int partId, int quantity) {
        // Implement your order creation logic here
    }

    public List<InventoryItem> getAllItems() throws SQLException {
        return inventoryDAO.getAllInventoryItems();
    }
}
