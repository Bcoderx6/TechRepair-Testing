package techrepairapp.ui;

import com.techrepairapp.ui.panels.CustomerPanel;
import com.techrepairapp.ui.panels.EmployeePanel;
import com.techrepairapp.ui.panels.InventoryPanel;
import com.techrepairapp.ui.panels.OrderPanel;
import com.techrepairapp.ui.panels.SupplierPanel;
import com.techrepairapp.ui.panels.ReportPanel;
import com.techrepairapp.ui.panels.NotificationPanel;
import com.techrepairapp.ui.panels.AllocationPanel;
import com.techrepairapp.ui.panels.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

    public Main() {
        setTitle("TechRepair Business Automation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the login frame
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        LoginPanel loginPanel = new LoginPanel();
        loginFrame.getContentPane().add(loginPanel, BorderLayout.CENTER);
        loginFrame.setVisible(true);

        loginPanel.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginPanel.isAuthenticated()) {
                    loginFrame.dispose();  // Close the login frame
                    setVisible(true);  // Show the main application frame
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid credentials", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Setup main frame content
        JTabbedPane tabbedPane = new JTabbedPane();

        // Adding tabs for different panels
        tabbedPane.add("Customers", new CustomerPanel());
        tabbedPane.add("Employees", new EmployeePanel());
        tabbedPane.add("Inventory", new InventoryPanel());
        tabbedPane.add("Orders", new OrderPanel());
        tabbedPane.add("Suppliers", new SupplierPanel());
        tabbedPane.add("Report", new ReportPanel());
        tabbedPane.add("Notification", new NotificationPanel());
        tabbedPane.add("Allocation", new AllocationPanel());

        // Add the tabbed pane to the content pane
        add(tabbedPane, BorderLayout.CENTER);

        // Hide the main frame initially
        setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint g = new GradientPaint(0, 0, Color.decode("#1CB5E0"), 0, getHeight(), Color.decode("#000046"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        super.paint(graphics);
    }
}
