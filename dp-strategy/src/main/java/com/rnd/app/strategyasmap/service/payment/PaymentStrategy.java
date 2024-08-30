package com.rnd.app.strategyasmap.service.payment;

public interface PaymentStrategy {

    void register();

    String execute(double amount);
}
