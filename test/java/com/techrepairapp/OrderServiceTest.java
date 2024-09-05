package com.techrepairapp;

import com.techrepairapp.models.Order;
import com.techrepairapp.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = new OrderService();
        // Add setup code here if needed
    }

    @Test
    public void testAddOrder() {
        Date orderDate = new Date();
        Order order = new Order(0, "John Doe", "Repair Service", orderDate, "Pending");
        orderService.addOrder(order);

        List<Order> orders = orderService.getAllOrders();
        assertEquals(1, orders.size());
        assertEquals("John Doe", orders.get(0).getCustomerName());
    }

    @Test
    public void testUpdateOrder() {
        Date orderDate = new Date();
        Order order = new Order(0, "Jane Doe", "Service Package", orderDate, "Completed");
        orderService.addOrder(order);

        List<Order> orders = orderService.getAllOrders();
        Order addedOrder = orders.get(0);
        addedOrder.setStatus("In Progress");
        orderService.updateOrder(addedOrder);

        orders = orderService.getAllOrders();
        assertEquals("In Progress", orders.get(0).getStatus());
    }

    @Test
    public void testDeleteOrder() {
        Date orderDate = new Date();
        Order order = new Order(0, "Alice Smith", "Maintenance", orderDate, "Pending");
        orderService.addOrder(order);

        List<Order> orders = orderService.getAllOrders();
        Order addedOrder = orders.get(0);
        orderService.deleteOrder(addedOrder.getId());

        orders = orderService.getAllOrders();
        assertEquals(0, orders.size());
    }
}