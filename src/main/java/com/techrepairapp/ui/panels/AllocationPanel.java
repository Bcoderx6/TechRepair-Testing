package com.techrepairapp.ui.panels;

import com.techrepairapp.models.Employee;
import com.techrepairapp.models.Order;
import com.techrepairapp.services.AllocationService;
import com.techrepairapp.services.EmployeeService;
import com.techrepairapp.services.OrderService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AllocationPanel extends JPanel {
    private final AllocationService allocationService;
    private final OrderService orderService;
    private final EmployeeService employeeService;
    private final JComboBox<Order> orderComboBox;
    private final JComboBox<Employee> employeeComboBox;

    public AllocationPanel() {
        allocationService = new AllocationService();
        orderService = new OrderService();
        employeeService = new EmployeeService();

        setLayout(new BorderLayout());
        setOpaque(false);

        // Create and set background gradient
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
        add(backgroundPanel, BorderLayout.CENTER);

        // Fetch orders and employees
        List<Order> orders = orderService.getAllOrders();
        List<Employee> employees = employeeService.getAllEmployees();

        // Populate combo boxes
        orderComboBox = new JComboBox<>(orders.toArray(new Order[0]));
        employeeComboBox = new JComboBox<>(employees.toArray(new Employee[0]));

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Select Order:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(orderComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Select Employee:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(employeeComboBox, gbc);

        backgroundPanel.add(inputPanel, BorderLayout.CENTER);

        // Create and customize the "Allocate Employee to Order" button
        JButton allocateButton = new JButton("Allocate Employee to Order");
        allocateButton.setPreferredSize(new Dimension(300, 50));
        allocateButton.setBackground(new Color(34, 139, 34));  // Dark green
        allocateButton.setForeground(Color.WHITE);
        allocateButton.setFocusPainted(false);
        allocateButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        allocateButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        allocateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect to the button
        allocateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                allocateButton.setBackground(new Color(50, 205, 50));  // Lighter green on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                allocateButton.setBackground(new Color(34, 139, 34));  // Dark green when not hovered
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(allocateButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for the button
        allocateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Order selectedOrder = (Order) orderComboBox.getSelectedItem();
                Employee selectedEmployee = (Employee) employeeComboBox.getSelectedItem();
                if (selectedOrder != null && selectedEmployee != null) {
                    allocationService.allocateEmployeeToOrder(selectedOrder.getId(), selectedEmployee.getId());
                    JOptionPane.showMessageDialog(null, "Employee allocated to order successfully!");
                }
            }
        });
    }
}
