package com.rnd.app.strategyasmap.service.payment;

import com.rnd.app.strategyasmap.model.payment.PaymentMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PaymentService implements PaymentExecutor {
    private Map<PaymentMode, PaymentStrategy> paymentStrategies = new HashMap<>(10);

    public void addPaymentStrategy(PaymentStrategy paymentStrategy, PaymentMode paymentMode) {
        paymentStrategies.put(paymentMode, paymentStrategy);
    }

    @Override
    public String processPayment(PaymentMode mode, double amount) {
        return paymentStrategies.get(mode).execute(amount);
    }
}
