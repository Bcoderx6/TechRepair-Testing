package com.techrepairapp.models;

public class Employee {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String role;

    public Employee(int id, String name, String email, String phone, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Override the toString() method for better readability
    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s', email='%s', phone='%s', role='%s'}",
                id, name, email != null ? email : "null", phone != null ? phone : "null", role);
    }
}
