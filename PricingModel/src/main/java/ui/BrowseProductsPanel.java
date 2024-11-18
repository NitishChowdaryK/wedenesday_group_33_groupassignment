/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import model.Business.Business;
import model.ProductManagement.Product;

public class BrowseProductsPanel extends JPanel {

    private Business business;
    private JTable productTable;
    private JButton detailsButton;
    private JTextField filterField;

    public BrowseProductsPanel(Business business) {
        this.business = business;
        setLayout(new BorderLayout());

        String[] columnNames = {"Name of the Product", "Base Price", "Max Price", "Goal Price"};
        Object[][] data = business.getSupplierDirectory().getSupplierList().stream()
                .flatMap(supplier -> supplier.getProductCatalog().getProductList().stream())
                .map(product -> new Object[]{product.toString(), product.getFloorPrice(), product.getCeilingPrice(), product.getTargetPrice()})
                .toArray(Object[][]::new);

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        productTable = new JTable(model);
        productTable.setAutoCreateRowSorter(true); // Enable sorting
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        // Filter Field
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.add(new JLabel("Filter Products: "), BorderLayout.WEST);
        filterField = new JTextField();
        filterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
                productTable.setRowSorter(sorter);
                String text = filterField.getText().trim();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        filterPanel.add(filterField, BorderLayout.CENTER);
        add(filterPanel, BorderLayout.NORTH);

        // Add a button to view details
        detailsButton = new JButton("View Details");
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int modelRow = productTable.convertRowIndexToModel(selectedRow); 
                    String productName = (String) model.getValueAt(modelRow, 0);

                    Product selectedProduct = business.getSupplierDirectory().getSupplierList().stream()
                            .flatMap(supplier -> supplier.getProductCatalog().getProductList().stream())
                            .filter(product -> product.toString().equals(productName))
                            .findFirst().orElse(null);

                    if (selectedProduct != null) {
                        JOptionPane.showMessageDialog(BrowseProductsPanel.this,
                                "Name of the Product: " + selectedProduct.toString() + "\n" +
                                "Base Price: " + selectedProduct.getFloorPrice() + "\n" +
                                "Max Price: " + selectedProduct.getCeilingPrice() + "\n" +
                                "Goal Price: " + selectedProduct.getTargetPrice(),
                                "Details of the Product", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(BrowseProductsPanel.this, "Product details not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(BrowseProductsPanel.this, "Please select a product to view details.");
                }
            }
        });
        add(detailsButton, BorderLayout.SOUTH);
    }
}