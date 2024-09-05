package com.techrepairapp.ui.panels;

import com.techrepairapp.models.InventoryItem;
import com.techrepairapp.services.InventoryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class InventoryPanel extends JPanel {
    private JTextField txtPartName;
    private JTextField txtQuantity;
    private JTextField txtReorderLevel;
    private JButton addButton;
    private JButton updateButton;
    private JButton removeButton;
    private JButton checkLowStockButton;
    private JButton purchaseButton;
    private JTable partsTable;
    private DefaultTableModel tableModel;
    private InventoryService inventoryService;

    public InventoryPanel() {
        this.inventoryService = new InventoryService();

        // Initialize UI components
        txtPartName = new JTextField(20);
        txtQuantity = new JTextField(10);
        txtReorderLevel = new JTextField(10);
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        removeButton = new JButton("Remove");
        checkLowStockButton = new JButton("Check Low Stock");
        purchaseButton = new JButton("Purchase");
        partsTable = new JTable();
        tableModel = new DefaultTableModel(new String[]{"Part ID", "Part Name", "Quantity", "Reorder Level", "Last Order Date"}, 0);
        partsTable.setModel(tableModel);

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

        // Create input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 5, 5));
        inputPanel.setOpaque(false);
        inputPanel.add(new JLabel("Part Name:"));
        inputPanel.add(txtPartName);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(txtQuantity);
        inputPanel.add(new JLabel("Reorder Level:"));
        inputPanel.add(txtReorderLevel);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(checkLowStockButton);
        buttonPanel.add(purchaseButton);

        // Customize button appearance
        customizeButton(addButton, new Color(34, 139, 34), Color.WHITE, new Color(50, 205, 50)); // Dark green
        customizeButton(updateButton, new Color(255, 215, 0), Color.BLACK, new Color(255, 255, 0)); // Gold color
        customizeButton(removeButton, new Color(178, 34, 34), Color.WHITE, new Color(255, 69, 0)); // Darker red
        customizeButton(checkLowStockButton, new Color(70, 130, 180), Color.WHITE, new Color(100, 149, 237)); // Steel blue
        customizeButton(purchaseButton, new Color(255, 165, 0), Color.BLACK, new Color(255, 140, 0)); // Orange

        // Customize table
        partsTable.setRowHeight(30);
        partsTable.setBackground(new Color(50, 50, 50)); // Dark gray table background color
        partsTable.setForeground(Color.WHITE); // White table text color
        partsTable.getTableHeader().setBackground(new Color(30, 30, 30)); // Dark header
        partsTable.getTableHeader().setForeground(Color.WHITE);
        partsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        // Create a JScrollPane and set its size
        JScrollPane scrollPane = new JScrollPane(partsTable);
        scrollPane.setPreferredSize(new Dimension(600, 300)); // Set size of the scroll pane
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Add components to panel
        backgroundPanel.add(inputPanel, BorderLayout.NORTH);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add background panel to the main panel
        add(backgroundPanel, BorderLayout.CENTER);

        // Add button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPart();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = partsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int partId = (int) tableModel.getValueAt(selectedRow, 0);
                    updatePart(partId);
                } else {
                    JOptionPane.showMessageDialog(null, "Select a part to update.");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = partsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int partId = (int) tableModel.getValueAt(selectedRow, 0);
                    removePart(partId);
                } else {
                    JOptionPane.showMessageDialog(null, "Select a part to remove.");
                }
            }
        });

        checkLowStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLowStock();
            }
        });

        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchaseParts();
            }
        });

        // Refresh table data on panel creation
        refreshTable();
    }

    private void customizeButton(JButton button, Color backgroundColor, Color textColor, Color hoverColor) {
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
    }

    private void addPart() {
        String partName = txtPartName.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        int reorderLevel = Integer.parseInt(txtReorderLevel.getText());

        try {
            inventoryService.addPart(new InventoryItem(0, partName, quantity, reorderLevel, null)); // partId will be auto-generated
            refreshTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updatePart(int partId) {
        String partName = txtPartName.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        int reorderLevel = Integer.parseInt(txtReorderLevel.getText());

        try {
            inventoryService.updatePart(new InventoryItem(partId, partName, quantity, reorderLevel, null));
            refreshTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void removePart(int partId) {
        try {
            inventoryService.removePart(partId);
            refreshTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void checkLowStock() {
        try {
            List<InventoryItem> lowStockItems = inventoryService.getLowStockItems();
            tableModel.setRowCount(0); // Clear the table
            for (InventoryItem item : lowStockItems) {
                tableModel.addRow(new Object[]{item.getPartId(), item.getPartName(), item.getQuantity(), item.getReorderLevel(), item.getLastOrderDate()});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void purchaseParts() {
        int selectedRow = partsTable.getSelectedRow();
        if (selectedRow >= 0) {
            int partId = (int) tableModel.getValueAt(selectedRow, 0);
            int quantityPurchased = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity to purchase:"));
            try {
                inventoryService.purchasePart(partId, quantityPurchased);
                refreshTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a part to purchase.");
        }
    }

    private void refreshTable() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<InventoryItem> items = inventoryService.getAllItems(); // Fetch all items
                tableModel.setRowCount(0); // Clear the table
                for (InventoryItem item : items) {
                    tableModel.addRow(new Object[]{item.getPartId(), item.getPartName(), item.getQuantity(), item.getReorderLevel(), item.getLastOrderDate()});
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
