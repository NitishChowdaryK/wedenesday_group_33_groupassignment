package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Business.Business;
import model.ProductManagement.Product;

public class AdjustPricesPanel extends JPanel {

    private Business business;
    private JTable productTable;
    private JTextField newPriceField;
    private JButton adjustButton;
    private JButton autoAdjustButton;
    private JLabel statusLabel;

    public AdjustPricesPanel(Business business) {
        this.business = business;
        setLayout(new BorderLayout());

        // Display products in a JTable
        String[] columnNames = {"Name of the Product", "Base Price", "Max Price", "Goal Price"};
        Object[][] data = business.getSupplierDirectory().getSupplierList().stream()
                .flatMap(supplier -> supplier.getProductCatalog().getProductList().stream())
                .map(product -> new Object[]{product.toString(), product.getFloorPrice(), product.getCeilingPrice(), product.getTargetPrice()})
                .toArray(Object[][]::new);

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(model);
        productTable.setAutoCreateRowSorter(true);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Panel for adjusting price
        JPanel adjustPanel = new JPanel(new FlowLayout());
        adjustPanel.add(new JLabel("New Target Price:"));
        newPriceField = new JTextField(10);
        newPriceField.setToolTipText("Enter a new goal price within the valid range (between base and max prices).");
        adjustPanel.add(newPriceField);
        adjustButton = new JButton("Price Adjustjment");
        adjustButton.setToolTipText("Select this option to adjust the Goal price manually.");
        adjustPanel.add(adjustButton);
        statusLabel = new JLabel(" ");
        statusLabel.setToolTipText("Gives feedback on adjustment of price operations.");
        adjustPanel.add(statusLabel);

        autoAdjustButton = new JButton("Auto Adjust Prices");
        autoAdjustButton.setToolTipText("Adjust the Goal price automatically based on the sales performance.");
        adjustPanel.add(autoAdjustButton);

        add(adjustPanel, BorderLayout.SOUTH);

        adjustButton.addActionListener(new ActionListener() {
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
                        String newPriceText = newPriceField.getText().trim();
                        if (newPriceText.isEmpty()) {
                            JOptionPane.showMessageDialog(AdjustPricesPanel.this,
                                    "Please enter a new target price.",
                                    "Input Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        try {
                            int newTargetPrice = Integer.parseInt(newPriceText);
                            if (newTargetPrice >= selectedProduct.getFloorPrice() && newTargetPrice <= selectedProduct.getCeilingPrice()) {
                                selectedProduct.updateProduct(selectedProduct.getFloorPrice(), selectedProduct.getCeilingPrice(), newTargetPrice);
                                model.setValueAt(newTargetPrice, modelRow, 3); // Update display
                                JOptionPane.showMessageDialog(AdjustPricesPanel.this,
                                        "Successfully adjusted the price of the product.: " + productName,
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE);
                                statusLabel.setText("Price is adjusted for: " + productName);
                            } else {
                                JOptionPane.showMessageDialog(AdjustPricesPanel.this,
                                        "Price Invalid: must be range between base and max prices.",
                                        "Input Invalid",
                                        JOptionPane.WARNING_MESSAGE);
                                statusLabel.setText("Price entry Invalid.");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(AdjustPricesPanel.this,
                                    "Kindly enter a valid number.",
                                    "Input Error",
                                    JOptionPane.ERROR_MESSAGE);
                            statusLabel.setText("Number format Invalid.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(AdjustPricesPanel.this,
                                "Product not found.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        statusLabel.setText("Product not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(AdjustPricesPanel.this,
                            "Kindly select a product to adjust.",
                            "Selection Error",
                            JOptionPane.WARNING_MESSAGE);
                    statusLabel.setText("No product is selected.");
                }
            }
        });

        autoAdjustButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                business.adjustPricesBasedOnPerformance(); 
                JOptionPane.showMessageDialog(AdjustPricesPanel.this,
                        "Successfully adjusted the price of the product based on the sales performance.",
                        "Auto Adjustment of prices is completed",
                        JOptionPane.INFORMATION_MESSAGE);
                refreshTableData(model);
                statusLabel.setText("Auto adjustment is completed.");
            }
        });

        productTable.getSelectionModel().addListSelectionListener(e -> statusLabel.setText(" "));
    }

    private void refreshTableData(DefaultTableModel model) {
        Object[][] updatedData = business.getSupplierDirectory().getSupplierList().stream()
                .flatMap(supplier -> supplier.getProductCatalog().getProductList().stream())
                .map(product -> new Object[]{product.toString(), product.getFloorPrice(), product.getCeilingPrice(), product.getTargetPrice()})
                .toArray(Object[][]::new);

        model.setDataVector(updatedData, new String[]{"Product Name", "Base Price", "Max Price", "Goal Price"});
    }
}