package com.techrepairapp.ui.panels;

import com.techrepairapp.models.Customer;
import com.techrepairapp.models.Employee;
import com.techrepairapp.services.NotificationService;
import com.techrepairapp.services.CustomerService;
import com.techrepairapp.services.EmployeeService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NotificationPanel extends JPanel {
    private final NotificationService notificationService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    private final JComboBox<Customer> customerComboBox;
    private final JComboBox<Employee> employeeComboBox;
    private final JTextField messageField;

    public NotificationPanel() {
        notificationService = new NotificationService();
        customerService = new CustomerService();
        employeeService = new EmployeeService();

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

        // Fetch customers and employees
        List<Customer> customers = customerService.getAllCustomers();
        List<Employee> employees = employeeService.getAllEmployees();

        // Populate combo boxes
        customerComboBox = new JComboBox<>(customers.toArray(new Customer[0]));
        employeeComboBox = new JComboBox<>(employees.toArray(new Employee[0]));
        messageField = new JTextField();

        // Set preferred width for message field
        messageField.setPreferredSize(new Dimension(300, 25));  // 300px width and 25px height

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);  // Transparent input panel
        inputPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(60, 10, 10, 60);  // Padding around components
        gbc.anchor = GridBagConstraints.WEST;     // Align components to the left
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Select Customer:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(customerComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Select Employee:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(employeeComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Message:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(messageField, gbc);

        backgroundPanel.add(inputPanel, BorderLayout.CENTER);

        // Create and customize the "Send Notification" button
        JButton notifyButton = new JButton("Send Notification");
        notifyButton.setPreferredSize(new Dimension(350, 50));
        notifyButton.setBackground(new Color(34, 139, 34));  // Dark green
        notifyButton.setForeground(Color.WHITE);
        notifyButton.setFocusPainted(false);
        notifyButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        notifyButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        notifyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect to the button
        notifyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                notifyButton.setBackground(new Color(50, 205, 50));  // Lighter green on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                notifyButton.setBackground(new Color(34, 139, 34));  // Dark green when not hovered
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);  // Transparent button panel
        buttonPanel.add(notifyButton);

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the background panel to the main panel
        add(backgroundPanel, BorderLayout.CENTER);

        // Action listener for the button
        notifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                Customer selectedCustomer = (Customer) customerComboBox.getSelectedItem();
                Employee selectedEmployee = (Employee) employeeComboBox.getSelectedItem();
                if (selectedCustomer != null) {
                    notificationService.notifyCustomer(selectedCustomer, message);
                    JOptionPane.showMessageDialog(null, "Notification sent to customer!");
                }
                if (selectedEmployee != null) {
                    notificationService.notifyEmployee(selectedEmployee, message);
                    JOptionPane.showMessageDialog(null, "Notification sent to employee!");
                }
            }
        });
    }
}

