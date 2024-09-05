package com.techrepairapp.services;

import com.techrepairapp.database.AllocationDAO;
import com.techrepairapp.database.EmployeeDAO;
import com.techrepairapp.database.OrderDAO;
import com.techrepairapp.models.Employee;
import com.techrepairapp.models.Order;

public class AllocationService {
    private AllocationDAO allocationDAO;
    private EmployeeDAO employeeDAO;
    private OrderDAO orderDAO;

    public AllocationService() {
        this.allocationDAO = new AllocationDAO();
        this.employeeDAO = new EmployeeDAO();
        this.orderDAO = new OrderDAO();
    }

    public void allocateEmployeeToOrder(int employeeId, int orderId) {
        // Fetch Employee and Order using their IDs
        Employee employee = employeeDAO.getEmployeeById(employeeId); // Ensure this method exists in EmployeeDAO
        Order order = orderDAO.getOrderById(orderId); // Ensure this method exists in OrderDAO

        if (employee != null && order != null) {
            allocationDAO.allocate(employee, order);
        } else {
            // Handle the case where the employee or order is not found
            System.out.println("Employee or Order not found");
        }
    }
}
