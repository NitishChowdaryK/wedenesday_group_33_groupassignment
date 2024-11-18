/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import javax.swing.*;
import java.awt.*;
import model.Business.Business;
import model.Business.ConfigureABusiness;

public class PricingApp extends JFrame {

    private Business business;

    public PricingApp() {
        business = ConfigureABusiness.initialize();

        setTitle("Pricing Model Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupUI();
    }

    private void setupUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Browse New Products", new BrowseProductsPanel(business));
        tabbedPane.addTab("Adjust the Prices", new AdjustPricesPanel(business));
        tabbedPane.addTab("Simulate", new SimulationPanel(business));
        tabbedPane.addTab("Check Reports", new ReportsPanel(business));

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PricingApp app = new PricingApp();
            app.setVisible(true);
        });
    }
}