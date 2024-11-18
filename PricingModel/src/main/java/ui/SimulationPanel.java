package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Business.Business;
import model.ProductManagement.Product;
import model.Supplier.Supplier;

public class SimulationPanel extends JPanel {

    private Business business;
    private JButton runSimulationButton;
    private JButton maximizeProfitButton;
    private JTextArea resultsArea;
    private JTextField percentageField;

    public SimulationPanel(Business business) {
        this.business = business;
        setLayout(new BorderLayout());

        // Panel for user input
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Percentage Change:"));
        percentageField = new JTextField(5);
        inputPanel.add(percentageField);
        runSimulationButton = new JButton("Run Simulation");
        inputPanel.add(runSimulationButton);
        add(inputPanel, BorderLayout.NORTH);

        // Text area to display results
        resultsArea = new JTextArea(10, 50);
        resultsArea.setEditable(false);
        add(new JScrollPane(resultsArea), BorderLayout.CENTER);

        // Action listener for running the simulation
        runSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate input
                String input = percentageField.getText().trim();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(SimulationPanel.this, 
                            "Please enter a percentage change.", 
                            "Input Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double percentageChange;
                try {
                    percentageChange = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SimulationPanel.this, 
                            "Invalid input. Please enter a valid number.", 
                            "Input Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Run simulation
                StringBuilder results = new StringBuilder();
                results.append("Simulation Results:\n");
                results.append(String.format("Applied Percentage Change: %.2f%%\n\n", percentageChange));

                for (Supplier supplier : business.getSupplierDirectory().getSupplierList()) {
                    for (Product product : supplier.getProductCatalog().getProductList()) {
                        int originalTargetPrice = product.getTargetPrice();
                        int newTargetPrice = (int) (originalTargetPrice * (1 + percentageChange / 100));
                        product.updateProduct(product.getFloorPrice(), product.getCeilingPrice(), newTargetPrice);
                        results.append(String.format("Product: %s, Old Target Price: %d, New Target Price: %d\n",
                                product.toString(), originalTargetPrice, newTargetPrice));
                    }
                }

                JOptionPane.showMessageDialog(SimulationPanel.this, 
                        "Simulation completed successfully.", 
                        "Simulation Complete", 
                        JOptionPane.INFORMATION_MESSAGE);
                resultsArea.setText(results.toString());
            }
        });

        // Tooltips
        percentageField.setToolTipText("Enter the percentage change for target prices (e.g., 10 for 10%).");
        runSimulationButton.setToolTipText("Click to run the simulation and update target prices.");
        resultsArea.setToolTipText("View the simulation results here.");

        // Adding the maximize profit button
        maximizeProfitButton = new JButton("Maximize Profit");
        maximizeProfitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                business.runProfitMaximizationSimulation();
                JOptionPane.showMessageDialog(SimulationPanel.this, 
                        "Profit maximization completed.", 
                        "Maximization Complete", 
                        JOptionPane.INFORMATION_MESSAGE);
                resultsArea.setText("Profit maximization completed.");
            }
        });
        maximizeProfitButton.setToolTipText("Click to maximize profit through iterative price adjustments.");
        add(maximizeProfitButton, BorderLayout.SOUTH);
    }
}