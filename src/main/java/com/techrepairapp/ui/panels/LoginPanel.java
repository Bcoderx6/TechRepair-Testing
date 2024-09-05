package com.techrepairapp.ui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JLabel messageLabel;
    private boolean authenticated = false;

    public LoginPanel() {
        setOpaque(false);

        // Create a gradient background panel
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
        backgroundPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Username Label and Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        backgroundPanel.add(usernameField, gbc);

        // Password Label and Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        backgroundPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        backgroundPanel.add(passwordField, gbc);

        // Login Button
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginButton = new JButton("Login");
        customizeLoginButton(loginButton);
        backgroundPanel.add(loginButton, gbc);

        // Message Label
        gbc.gridy = 3;
        messageLabel = new JLabel();
        backgroundPanel.add(messageLabel, gbc);

        // Add Action Listener to Login Button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // Add background panel to the main panel
        setLayout(new BorderLayout());
        add(backgroundPanel, BorderLayout.CENTER);
    }

    // Customizing the login button
    private void customizeLoginButton(JButton loginButton) {
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginButton.setBackground(new Color(34, 139, 34));  // Dark green
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect for the login button
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(50, 205, 50));  // Lighter green on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(34, 139, 34));  // Dark green when not hovered
            }
        });
    }

    // Handle login logic
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Debugging information
        System.out.println("Attempting login with Username: " + username);
        System.out.println("Attempting login with Password: " + password);

        // Clear previous message
        messageLabel.setText("");

        // Example check
        if ("admin".equals(username) && "password".equals(password)) {
            authenticated = true;
            messageLabel.setText("Login successful");
            messageLabel.setForeground(Color.GREEN);
        } else {
            authenticated = false;
            messageLabel.setText("Invalid credentials");
            messageLabel.setForeground(Color.RED);
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
