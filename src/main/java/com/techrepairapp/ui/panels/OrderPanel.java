package com.techrepairapp.ui.panels;

import com.techrepairapp.models.Order;
import com.techrepairapp.services.OrderService;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrderPanel extends JPanel {
    private final OrderService orderService;
    private final JTable orderTable;
    private final DefaultTableModel tableModel;

    public OrderPanel() {
        orderService = new OrderService();

        // Set gradient background color using custom paint
        setOpaque(false);
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(67, 183, 186);  // Start color
                Color color2 = new Color(100, 211, 177); // End color
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setLayout(new BorderLayout());

        // Table Model with columns for ID, Customer Name, Details, Order Date, and Status
        tableModel = new DefaultTableModel(new Object[]{"ID", "Customer Name", "Details", "Order Date", "Status"}, 0);
        orderTable = new JTable(tableModel);

        // Customizing table column sizes
        TableColumnModel columnModel = orderTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  // ID
        columnModel.getColumn(1).setPreferredWidth(150); // Customer Name
        columnModel.getColumn(2).setPreferredWidth(200); // Details
        columnModel.getColumn(3).setPreferredWidth(150); // Order Date
        columnModel.getColumn(4).setPreferredWidth(150); // Status

        // Customizing row height and table colors
        orderTable.setRowHeight(30);
        orderTable.setBackground(new Color(50, 50, 50)); // Dark gray table background
        orderTable.setForeground(new Color(255, 255, 255)); // White table text color

        // Customize header colors
        orderTable.getTableHeader().setBackground(new Color(30, 30, 30)); // Dark header
        orderTable.getTableHeader().setForeground(Color.WHITE);
        orderTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        // Create a JScrollPane and set its size
        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setPreferredSize(new Dimension(550, 300)); // Set size of the scroll pane
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Create a panel to center the table
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setOpaque(false); // Make transparent
        tablePanel.add(Box.createVerticalGlue()); // Add glue to center vertically
        tablePanel.add(scrollPane);
        tablePanel.add(Box.createVerticalGlue()); // Add glue to center vertically

        // Add tablePanel to the center of the main panel
        backgroundPanel.add(tablePanel, BorderLayout.CENTER);

        // Load initial order data
        loadOrderData();

        // Button panel to hold Add, Remove, and Update buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setOpaque(false); // Make transparent

        JButton addButton = new JButton("Add Order");
        JButton removeButton = new JButton("Remove Order");
        JButton updateButton = new JButton("Update Order");

        // Customize button appearance
        addButton.setBackground(new Color(34, 139, 34)); // Dark green
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        addButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        removeButton.setBackground(new Color(178, 34, 34)); // Darker red
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);
        removeButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        removeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        removeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        updateButton.setBackground(new Color(255, 215, 0)); // Gold color
        updateButton.setForeground(Color.BLACK);
        updateButton.setFocusPainted(false);
        updateButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        updateButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect to buttons
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addButton.setBackground(new Color(50, 205, 50)); // Lighter green
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addButton.setBackground(new Color(34, 139, 34)); // Darker green
            }
        });
        removeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeButton.setBackground(new Color(255, 69, 0)); // Lighter red
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                removeButton.setBackground(new Color(178, 34, 34)); // Darker red
            }
        });
        updateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                updateButton.setBackground(new Color(255, 255, 0)); // Lighter yellow
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                updateButton.setBackground(new Color(255, 215, 0)); // Darker gold
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);

        // Add button panel at the bottom
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add background panel to the main panel
        add(backgroundPanel, BorderLayout.CENTER);

        // Add Order Action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerName = JOptionPane.showInputDialog("Enter Customer Name");
                String details = JOptionPane.showInputDialog("Enter Details");
                String orderDateStr = JOptionPane.showInputDialog("Enter Order Date (yyyy-mm-dd)");
                String status = JOptionPane.showInputDialog("Enter Status");

                try {
                    java.util.Date orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(orderDateStr);
                    if (customerName != null && details != null && status != null) {
                        Order order = new Order(0, customerName, details, orderDate, status);
                        orderService.addOrder(order);
                        loadOrderData();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(OrderPanel.this, "Invalid date format.");
                }
            }
        });

        // Remove Order Action
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    orderService.deleteOrder(id);
                    loadOrderData();
                } else {
                    JOptionPane.showMessageDialog(OrderPanel.this, "No order selected.");
                }
            }
        });

        // Update Order Action
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    String customerName = JOptionPane.showInputDialog("Enter new Customer Name");
                    String details = JOptionPane.showInputDialog("Enter new Details");
                    String orderDateStr = JOptionPane.showInputDialog("Enter new Order Date (yyyy-mm-dd)");
                    String status = JOptionPane.showInputDialog("Enter new Status");

                    try {
                        java.util.Date orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(orderDateStr);
                        if (customerName != null && details != null && status != null) {
                            Order order = new Order(id, customerName, details, orderDate, status);
                            orderService.updateOrder(order);
                            loadOrderData();
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(OrderPanel.this, "Invalid date format.");
                    }
                } else {
                    JOptionPane.showMessageDialog(OrderPanel.this, "No order selected.");
                }
            }
        });
    }

    private void loadOrderData() {
        // Clear existing data in the table model
        tableModel.setRowCount(0);

        // Fetch the list of orders from the OrderService
        try {
            List<Order> orders = orderService.getAllOrders();

            // Add each order to the table model
            for (Order order : orders) {
                // Format the order date as a string
                String orderDateStr = new SimpleDateFormat("yyyy-MM-dd").format(order.getOrderDate());

                // Add the order data as a new row in the table model
                tableModel.addRow(new Object[]{
                        order.getId(),
                        order.getCustomerName(),
                        order.getDetails(),
                        orderDateStr,
                        order.getStatus()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading order data.");
        }
    }
}
