package com.ak.learning.patterns.strategy;

import java.util.HashSet;
import java.util.Set;

public class LargeOrderPromotion implements Promotion {
    @Override
    public String getName() {
        return "Large Order Discount";
    }

    @Override
    public double getDiscount(Order order) {
        /* 7% discount for orders with 10 or more distinct items */
        Set<String> distinctItems = new HashSet<>();
        order.getLineItems().forEach(lineItem -> {
            distinctItems.add(lineItem.getName());
        });

        if (distinctItems.size() >= 10) {
            return order.getTotal() * 0.7;
        }
        return 0.0;
    }
}
