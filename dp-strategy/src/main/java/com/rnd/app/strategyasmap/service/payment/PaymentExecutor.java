package com.rnd.app.strategyasmap.service.payment;

import com.rnd.app.strategyasmap.model.payment.PaymentMode;

public interface PaymentExecutor {
    String processPayment(PaymentMode mode, double amount);
}
