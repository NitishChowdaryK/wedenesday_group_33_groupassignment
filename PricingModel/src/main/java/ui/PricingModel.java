/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import model.Business.Business;
import model.Business.ConfigureABusiness;

/**
 *
 * @author kal bugrara
 */
public class PricingModel {

    public static void main(String[] args) {
        Business business = ConfigureABusiness.initialize();

        JFrame frame = new JFrame("Pricing Model Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set desired size
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Browse for Products", new BrowseProductsPanel(business));
        tabbedPane.addTab("Adjust the Prices of products", new AdjustPricesPanel(business));
        tabbedPane.addTab("Simulate", new SimulationPanel(business));
        tabbedPane.addTab("Generate the Reports", new ReportsPanel(business));

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}