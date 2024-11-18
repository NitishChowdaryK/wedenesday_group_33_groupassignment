
package model.OrderManagement;

import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class MasterOrderReport {

    private ArrayList<OrderSummary> orderSummaryList;

    public MasterOrderReport() {
        this.orderSummaryList = new ArrayList<>();
    }

    public void generateOrderReport(ArrayList<Order> orders) {
        for (Order order : orders) {
            OrderSummary orderSummary = new OrderSummary(order);
            orderSummaryList.add(orderSummary);
        }
    }

    public OrderSummary getTopProfitableOrder() {
        OrderSummary topOrder = null;
        for (OrderSummary orderSummary : orderSummaryList) {
            if (topOrder == null || orderSummary.getOrderProfit() > topOrder.getOrderProfit()) {
                topOrder = orderSummary;
            }
        }
        return topOrder;
    }

    public ArrayList<OrderSummary> getOrderSummaryList() {
        return orderSummaryList;
    }
}

