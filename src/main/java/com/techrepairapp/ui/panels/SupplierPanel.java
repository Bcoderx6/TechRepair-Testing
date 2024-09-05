package com.techrepairapp.ui.panels;

import com.techrepairapp.models.Supplier;
import com.techrepairapp.services.SupplierService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SupplierPanel extends JPanel {
    private final SupplierService supplierService;
    private final JTable supplierTable;
    private final DefaultTableModel tableModel;

    public SupplierPanel() {
        supplierService = new SupplierService();

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

        // Table Model with columns for ID, Name, Contact Info, and Address
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Contact Info", "Address"}, 0);
        supplierTable = new JTable(tableModel);

        // Customizing row height and table colors
        supplierTable.setRowHeight(30);
        supplierTable.setBackground(new Color(50, 50, 50)); // Dark gray table background
        supplierTable.setForeground(new Color(255, 255, 255)); // White table text color

        // Customize header colors
        supplierTable.getTableHeader().setBackground(new Color(30, 30, 30)); // Dark header
        supplierTable.getTableHeader().setForeground(Color.WHITE);
        supplierTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        // Create a JScrollPane and set its size
        JScrollPane scrollPane = new JScrollPane(supplierTable);
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

        // Load initial supplier data
        loadSupplierData();

        // Button panel to hold Add, Remove, and Update buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setOpaque(false); // Make transparent

        JButton addButton = new JButton("Add Supplier");
        JButton removeButton = new JButton("Remove Supplier");
        JButton updateButton = new JButton("Update Supplier");

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

        // Add Supplier Action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter Supplier Name");
                String contactInfo = JOptionPane.showInputDialog("Enter Contact Info");
                String address = JOptionPane.showInputDialog("Enter Address");

                if (name != null && contactInfo != null && address != null) {
                    Supplier supplier = new Supplier(0, name, contactInfo, address);
                    supplierService.addSupplier(supplier);
                    loadSupplierData();
                }
            }
        });

        // Remove Supplier Action
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = supplierTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    supplierService.deleteSupplier(id);
                    loadSupplierData();
                } else {
                    JOptionPane.showMessageDialog(SupplierPanel.this, "No supplier selected.");
                }
            }
        });

        // Update Supplier Action
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = supplierTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    String name = JOptionPane.showInputDialog("Enter new Supplier Name");
                    String contactInfo = JOptionPane.showInputDialog("Enter new Contact Info");
                    String address = JOptionPane.showInputDialog("Enter new Address");

                    if (name != null && contactInfo != null && address != null) {
                        Supplier supplier = new Supplier(id, name, contactInfo, address);
                        supplierService.updateSupplier(supplier);
                        loadSupplierData();
                    }
                } else {
                    JOptionPane.showMessageDialog(SupplierPanel.this, "No supplier selected.");
                }
            }
        });
    }

    private void loadSupplierData() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        tableModel.setRowCount(0);
        for (Supplier supplier : suppliers) {
            tableModel.addRow(new Object[]{
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getContactInfo(),
                    supplier.getAddress()
            });
        }
    }
}
