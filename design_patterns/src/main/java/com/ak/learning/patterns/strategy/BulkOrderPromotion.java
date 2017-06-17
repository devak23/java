package com.ak.learning.patterns.strategy;

public class BulkOrderPromotion implements Promotion {
    @Override
    public String getName() {
        return "Bulk Order Discount";
    }

    @Override
    public double getDiscount(Order order) {
        /* 10% discount for each line item with 20 or more units */
        double sumTotal = 0.0;
        for (LineItem item : order.getLineItems()) {
            if (item.getQuantity() >= 20) {
                sumTotal += order.getTotal() * 0.1;
            }
        }
        return sumTotal;
    }
}
