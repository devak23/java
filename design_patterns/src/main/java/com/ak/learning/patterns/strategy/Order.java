package com.ak.learning.patterns.strategy;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<LineItem> lineItems;
    private Customer customer;
    private Promotion promotion;

    public Order(Customer customer, Promotion promotion) {
        this.lineItems = new ArrayList<>();
        this.customer = customer;
        this.promotion = promotion;
    }

    public Order addItem(LineItem item) {
        lineItems.add(item);
        return this;
    }

    public List<LineItem> getLineItems() {
        return new ArrayList<>(lineItems);
    }

    public double getTotal() {
        double sumTotal = 0.0d;
        for (LineItem item : lineItems) {
            sumTotal += item.getTotal();
        }
        return sumTotal;
    }

    public double due() {
        double discount = 0;
        if (promotion != null) {
            discount = promotion.getDiscount(this);
        }
        return getTotal() - discount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
