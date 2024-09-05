package com.techrepairapp.services;

import com.techrepairapp.database.SupplierDAO;
import com.techrepairapp.models.Supplier;

import java.util.List;

public class SupplierService {
    private final SupplierDAO supplierDAO;

    public SupplierService() {
        supplierDAO = new SupplierDAO();
    }

    public void addSupplier(Supplier supplier) {
        supplierDAO.addSupplier(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierDAO.getAllSuppliers();
    }

    public void updateSupplier(Supplier supplier) {
        supplierDAO.updateSupplier(supplier);
    }

    public void deleteSupplier(int id) {
        supplierDAO.deleteSupplier(id);
    }
}
