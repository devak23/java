package com.ak.learning.patterns.strategy;

public class LineItem {
    private String name;
    private int quantity;
    private double price;

    public LineItem(String name, int quantity, double price) {
        this.setName(name);
        this.setQuantity(quantity);
        this.setPrice(price);
    }

    public String getName() {
        return name;
    }

    public LineItem setName(String name) {
        this.name = name;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public LineItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public LineItem setPrice(double price) {
        this.price = price;
        return this;
    }

    public double getTotal() {
        return getPrice() * getQuantity();
    }
}
