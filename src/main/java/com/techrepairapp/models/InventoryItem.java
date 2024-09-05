package com.techrepairapp.models;

import java.util.Date;

public class InventoryItem {
    private int partId;
    private String partName;
    private int quantity;
    private int reorderLevel;
    private Date lastOrderDate;

    // Constructor
    public InventoryItem(int partId, String partName, int quantity, int reorderLevel, Date lastOrderDate) {
        this.partId = partId;
        this.partName = partName;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.lastOrderDate = lastOrderDate;
    }

    // Getters and Setters
    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Date getLastOrderDate() {
        return lastOrderDate;
    }

    public void setLastOrderDate(Date lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }
}