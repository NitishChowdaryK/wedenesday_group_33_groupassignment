/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.OrderManagement;

import java.util.ArrayList;

import model.CustomerManagement.CustomerProfile;
import model.MarketingManagement.MarketingPersonProfile;
import model.SalesManagement.SalesPersonProfile;

/**
 *
 * @author kal bugrara
 */
public class MasterOrderList {

    private ArrayList<Order> orders;
    private MasterOrderReport masterOrderReport;

    public MasterOrderList() {
        this.orders = new ArrayList<>();
    }

    public Order newOrder(CustomerProfile customerProfile) {
        Order order = new Order(customerProfile);
        orders.add(order);
        return order;
    }

    public Order newOrder(CustomerProfile customerProfile, SalesPersonProfile salesPersonProfile) {
        Order order = new Order(customerProfile, salesPersonProfile);
        orders.add(order);
        return order;
    }

    public MasterOrderReport generateMasterOrderReport() {
        masterOrderReport = new MasterOrderReport();
        masterOrderReport.generateOrderReport(orders);
        return masterOrderReport;
    }

    public int getSalesVolume() {
        int sum = 0;
        for (Order order : orders) {
            sum += order.getOrderTotal();
        }
        return sum;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}