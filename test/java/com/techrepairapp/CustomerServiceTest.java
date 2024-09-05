package com.techrepairapp;

import com.techrepairapp.models.Customer;
import com.techrepairapp.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerServiceTest {

    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerService = new CustomerService();
        // Add setup code here if needed
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer(0, "John Doe", "john.doe@example.com", "1234567890");
        customerService.addCustomer(customer);

        List<Customer> customers = customerService.getAllCustomers();
        assertEquals(1, customers.size());
        assertEquals("John Doe", customers.get(0).getName());
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer(0, "Jane Doe", "jane.doe@example.com", "0987654321");
        customerService.addCustomer(customer);

        List<Customer> customers = customerService.getAllCustomers();
        Customer addedCustomer = customers.get(0);
        addedCustomer.setName("Jane Smith");
        customerService.updateCustomer(addedCustomer);

        customers = customerService.getAllCustomers();
        assertEquals("Jane Smith", customers.get(0).getName());
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer(0, "Mark Smith", "mark.smith@example.com", "1122334455");
        customerService.addCustomer(customer);

        List<Customer> customers = customerService.getAllCustomers();
        Customer addedCustomer = customers.get(0);
        customerService.deleteCustomer(addedCustomer.getId());

        customers = customerService.getAllCustomers();
        assertEquals(0, customers.size());
    }
}