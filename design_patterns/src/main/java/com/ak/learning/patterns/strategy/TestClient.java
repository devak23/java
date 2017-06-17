package com.ak.learning.patterns.strategy;

public class TestClient {

    public static void main(String[] args) {
        Customer joe = new Customer("Joe").setPoints(2000);
        Promotion promotion = new LoyalCustomerPromotion();
        Order order = new Order(joe, promotion);
        order.addItem(new LineItem("apple", 4, 4.5))
                .addItem(new LineItem("banana", 2, 2.5))
                .addItem(new LineItem("orange", 2, 1.5));
        System.out.println("Order total = " + order.getTotal());
        System.out.println("Payment due = " + order.due());
    }
}
