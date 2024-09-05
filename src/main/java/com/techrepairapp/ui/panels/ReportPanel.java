package com.techrepairapp.ui.panels;

import com.techrepairapp.services.ReportService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportPanel extends JPanel {
    private final ReportService reportService;

    public ReportPanel() {
        reportService = new ReportService();

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

        // Create and customize the "Generate Monthly Report" button
        JButton generateReportButton = new JButton("Generate Monthly Report");
        generateReportButton.setPreferredSize(new Dimension(250, 50));
        generateReportButton.setBackground(new Color(34, 139, 34));  // Dark green
        generateReportButton.setForeground(Color.WHITE);
        generateReportButton.setFocusPainted(false);
        generateReportButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        generateReportButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        generateReportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect to the button
        generateReportButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                generateReportButton.setBackground(new Color(50, 205, 50)); // Lighter green on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                generateReportButton.setBackground(new Color(34, 139, 34)); // Dark green when not hovered
            }
        });

        // Center the button in the background panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);  // Transparent panel
        buttonPanel.setLayout(new GridBagLayout());  // Center the button
        buttonPanel.add(generateReportButton);

        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add the background panel to the main panel
        add(backgroundPanel, BorderLayout.CENTER);

        // Action listener for the button
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sparePartsReport = reportService.getSparePartsStockReport();
                String orderStatusReport = reportService.getOrderStatusReport();
                String partsBelowReorderReport = reportService.getPartsBelowReorderLevelReport();

                // Displaying the results in a message dialog
                JOptionPane.showMessageDialog(null,
                        sparePartsReport + "\n\n" + orderStatusReport + "\n\n" + partsBelowReorderReport,
                        "Monthly Report",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}