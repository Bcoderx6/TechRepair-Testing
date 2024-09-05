package com.techrepairapp;

import com.techrepairapp.models.InventoryItem;
import com.techrepairapp.services.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryServiceTest {

    private InventoryService inventoryService;

    @BeforeEach
    public void setUp() {
        inventoryService = new InventoryService();
        // Add setup code here if needed
    }

    @Test
    public void testAddItem() {
        InventoryItem item = new InventoryItem(0, "Laptop", 10, "Supplier A");
        inventoryService.addItem(item);

        List<InventoryItem> items = inventoryService.getAllItems();
        assertEquals(1, items.size());
        assertEquals("Laptop", items.get(0).getName());
    }

    @Test
    public void testUpdateItem() {
        InventoryItem item = new InventoryItem(0, "Mouse", 50, "Supplier B");
        inventoryService.addItem(item);

        List<InventoryItem> items = inventoryService.getAllItems();
        InventoryItem addedItem = items.get(0);
        addedItem.setName("Wireless Mouse");
        inventoryService.updateItem(addedItem);

        items = inventoryService.getAllItems();
        assertEquals("Wireless Mouse", items.get(0).getName());
    }

    @Test
    public void testDeleteItem() {
        InventoryItem item = new InventoryItem(0, "Keyboard", 30, "Supplier C");
        inventoryService.addItem(item);

        List<InventoryItem> items = inventoryService.getAllItems();
        InventoryItem addedItem = items.get(0);
        inventoryService.deleteItem(addedItem.getId());

        items = inventoryService.getAllItems();
        assertEquals(0, items.size());
    }
}