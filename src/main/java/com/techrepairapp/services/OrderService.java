package com.techrepairapp.services;

import com.techrepairapp.database.OrderDAO;
import com.techrepairapp.models.Order;

import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO;

    public OrderService() {
        orderDAO = new OrderDAO();
    }

    public void addOrder(Order order) {
        orderDAO.addOrder(order);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }

    public void deleteOrder(int id) {
        orderDAO.deleteOrder(id);
    }
}
