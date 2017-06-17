package com.ak.learning.patterns.strategy;

public class LoyalCustomerPromotion implements Promotion {
    @Override
    public String getName() {
        return "Loyal Customer Discount";
    }

    @Override
    public double getDiscount(Order order) {
        /* 5% discount for customers with 1000 or more fidelity points */
        if (order.getCustomer().getPoints() > 1000) {
            return order.getTotal() * 0.05;
        }

        return 0.0;
    }
}
