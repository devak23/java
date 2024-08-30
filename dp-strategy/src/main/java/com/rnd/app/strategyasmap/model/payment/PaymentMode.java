package com.rnd.app.strategyasmap.model.payment;

import lombok.Getter;

public enum PaymentMode {
    CREDIT_CARD("creditcard"),
    G_PAY("googlepay"),
    CASH("cash"),
    PAYPAL("paypal");

    @Getter
    private final String mode;
    PaymentMode(String mode) {
        this.mode = mode;
    }
}
