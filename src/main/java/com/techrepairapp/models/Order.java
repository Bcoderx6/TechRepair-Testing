package com.techrepairapp.models;

import java.util.Date;

public class Order {
    private int id;
    private String customerName;
    private String details;
    private Date orderDate;
    private String status;

    public Order() {
    }

    public Order(int id, String customerName, String details, Date orderDate, String status) {
        this.id = id;
        this.customerName = customerName;
        this.details = details;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", details='" + details + '\'' +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                '}';
    }
}
