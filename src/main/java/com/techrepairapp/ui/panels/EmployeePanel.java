package com.techrepairapp.ui.panels;

import com.techrepairapp.models.Employee;
import com.techrepairapp.services.EmployeeService;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeePanel extends JPanel {
    private final EmployeeService employeeService;
    private final JTable employeeTable;
    private final DefaultTableModel tableModel;

    public EmployeePanel() {
        employeeService = new EmployeeService();

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

        // Table Model with columns for ID, Name, Role, Contact Number, and Email
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Role", "Contact Number", "Email"}, 0);
        employeeTable = new JTable(tableModel);

        // Customizing table column sizes
        TableColumnModel columnModel = employeeTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  // ID
        columnModel.getColumn(1).setPreferredWidth(150); // Name
        columnModel.getColumn(2).setPreferredWidth(200); // Role
        columnModel.getColumn(3).setPreferredWidth(150); // Contact Number
        columnModel.getColumn(4).setPreferredWidth(200); // Email

        // Customizing row height and table colors
        employeeTable.setRowHeight(30);
        employeeTable.setBackground(new Color(50, 50, 50)); // Dark gray table background color
        employeeTable.setForeground(new Color(255, 255, 255)); // White table text color

        // Customize header colors
        employeeTable.getTableHeader().setBackground(new Color(30, 30, 30)); // Dark header
        employeeTable.getTableHeader().setForeground(Color.WHITE);
        employeeTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        // Create a JScrollPane and set its size
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setPreferredSize(new Dimension(650, 300)); // Set size of the scroll pane
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

        // Load initial employee data
        loadEmployeeData();

        // Button panel to hold Add, Remove, and Update buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setOpaque(false); // Make transparent

        JButton addButton = new JButton("Add Employee");
        JButton removeButton = new JButton("Remove Employee");
        JButton updateButton = new JButton("Update Employee");

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

        // Add Employee Action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter Name");
                String role = JOptionPane.showInputDialog("Enter Role");
                String contactNumber = JOptionPane.showInputDialog("Enter Contact Number");
                String email = JOptionPane.showInputDialog("Enter Email"); // Get email

                if (name != null && role != null && contactNumber != null && email != null) {
                    Employee employee = new Employee(0, name, role, contactNumber, email);
                    employeeService.addEmployee(employee);
                    loadEmployeeData();
                }
            }
        });

        // Remove Employee Action
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    employeeService.deleteEmployee(id);
                    loadEmployeeData();
                } else {
                    JOptionPane.showMessageDialog(EmployeePanel.this, "No employee selected.");
                }
            }
        });

        // Update Employee Action
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    String name = JOptionPane.showInputDialog("Enter new Name");
                    String role = JOptionPane.showInputDialog("Enter new Role");
                    String contactNumber = JOptionPane.showInputDialog("Enter new Contact Number");
                    String email = JOptionPane.showInputDialog("Enter new Email"); // Get new email

                    if (name != null && role != null && contactNumber != null && email != null) {
                        Employee employee = new Employee(id, name, role, contactNumber, email);
                        employeeService.updateEmployee(employee);
                        loadEmployeeData();
                    }
                } else {
                    JOptionPane.showMessageDialog(EmployeePanel.this, "No employee selected.");
                }
            }
        });
    }

    private void loadEmployeeData() {
        List<Employee> employees = employeeService.getAllEmployees();
        tableModel.setRowCount(0);  // Clear existing data
        for (Employee employee : employees) {
            tableModel.addRow(new Object[]{employee.getId(), employee.getName(), employee.getRole(), employee.getPhone(), employee.getEmail()});
        }
    }
}
