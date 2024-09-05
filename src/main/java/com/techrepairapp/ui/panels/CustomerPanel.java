package com.techrepairapp.ui.panels;

import com.techrepairapp.models.Customer;
import com.techrepairapp.services.CustomerService;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerPanel extends JPanel {
    private final CustomerService customerService;
    private final JTable customerTable;
    private final DefaultTableModel tableModel;

    public CustomerPanel() {
        customerService = new CustomerService();

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

        // Table Model with columns for ID, Name, Email, and Phone
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Phone"}, 0);
        customerTable = new JTable(tableModel);

        // Customizing table column sizes
        TableColumnModel columnModel = customerTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  // ID
        columnModel.getColumn(1).setPreferredWidth(150); // Name
        columnModel.getColumn(2).setPreferredWidth(200); // Email
        columnModel.getColumn(3).setPreferredWidth(150); // Phone

        // Customizing row height and table colors
        customerTable.setRowHeight(30);
        customerTable.setBackground(new Color(50, 50, 50)); // Dark gray table background color
        customerTable.setForeground(new Color(255, 255, 255)); // White table text color

        // Customize header colors
        customerTable.getTableHeader().setBackground(new Color(30, 30, 30));
        customerTable.getTableHeader().setForeground(Color.WHITE);
        customerTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        // Load initial customer data
        loadCustomerData();

        // Create a JScrollPane and set its size
        JScrollPane scrollPane = new JScrollPane(customerTable);
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

        // Button panel to hold Add, Remove, and Update buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setOpaque(false); // Make transparent

        JButton addButton = new JButton("Add Customer");
        JButton removeButton = new JButton("Remove Customer");
        JButton updateButton = new JButton("Update Customer");

        // Customize button appearance
        addButton.setBackground(new Color(34, 139, 34)); // Darker green
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

        // Add Customer Action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter Name");
                String email = JOptionPane.showInputDialog("Enter Email");
                String phone = JOptionPane.showInputDialog("Enter Phone");

                if (name != null && email != null && phone != null) {
                    Customer customer = new Customer(0, name, email, phone);
                    customerService.addCustomer(customer);
                    loadCustomerData();
                }
            }
        });

        // Remove Customer Action
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    customerService.deleteCustomer(id);
                    loadCustomerData();
                } else {
                    JOptionPane.showMessageDialog(CustomerPanel.this, "No customer selected.");
                }
            }
        });

        // Update Customer Action
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    String name = JOptionPane.showInputDialog("Enter new Name");
                    String email = JOptionPane.showInputDialog("Enter new Email");
                    String phone = JOptionPane.showInputDialog("Enter new Phone");

                    if (name != null && email != null && phone != null) {
                        Customer customer = new Customer(id, name, email, phone);
                        customerService.updateCustomer(customer);
                        loadCustomerData();
                    }
                } else {
                    JOptionPane.showMessageDialog(CustomerPanel.this, "No customer selected.");
                }
            }
        });
    }

    // Loads customer data from the service into the table
    private void loadCustomerData() {
        List<Customer> customers = customerService.getAllCustomers();
        tableModel.setRowCount(0);  // Clear existing data
        for (Customer customer : customers) {
            tableModel.addRow(new Object[]{
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getPhone()
            });
        }
    }
}

