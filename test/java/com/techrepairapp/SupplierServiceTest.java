package com.techrepairapp;

import com.techrepairapp.models.Supplier;
import com.techrepairapp.services.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupplierServiceTest {

    private SupplierService supplierService;

    @BeforeEach
    public void setUp() {
        supplierService = new SupplierService();
        // Add setup code here if needed
    }

    @Test
    public void testAddSupplier() {
        Supplier supplier = new Supplier(0, "Supplier X", "123-456-7890", "123 Supplier St.");
        supplierService.addSupplier(supplier);

        List<Supplier> suppliers = supplierService.getAllSuppliers();
        assertEquals(1, suppliers.size());
        assertEquals("Supplier X", suppliers.get(0).getName());
    }

    @Test
    public void testUpdateSupplier() {
        Supplier supplier = new Supplier(0, "Supplier Y", "987-654-3210", "456 Supplier Ave.");
        supplierService.addSupplier(supplier);

        List<Supplier> suppliers = supplierService.getAllSuppliers();
        Supplier addedSupplier = suppliers.get(0);
        addedSupplier.setName("Supplier Z");
        supplierService.updateSupplier(addedSupplier);

        suppliers = supplierService.getAllSuppliers();
        assertEquals("Supplier Z", suppliers.get(0).getName());
    }

    @Test
    public void testDeleteSupplier() {
        Supplier supplier = new Supplier(0, "Supplier A", "555-555-5555", "789 Supplier Blvd.");
        supplierService.addSupplier(supplier);

        List<Supplier> suppliers = supplierService.getAllSuppliers();
        Supplier addedSupplier = suppliers.get(0);
        supplierService.deleteSupplier(addedSupplier.getId());

        suppliers = supplierService.getAllSuppliers();
        assertEquals(0, suppliers.size());
    }
}