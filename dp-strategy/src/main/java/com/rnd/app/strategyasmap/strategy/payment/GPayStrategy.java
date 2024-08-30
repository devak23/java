package com.rnd.app.strategyasmap.strategy.payment;

import com.rnd.app.strategyasmap.model.payment.PaymentMode;
import com.rnd.app.strategyasmap.service.payment.PaymentService;
import com.rnd.app.strategyasmap.service.payment.PaymentStrategy;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GPayStrategy implements PaymentStrategy {
    @Autowired PaymentService paymentService;

    @Override
    @PostConstruct
    public void register() {
        paymentService.addPaymentStrategy(this, PaymentMode.G_PAY);
    }

    @Override
    public String execute(double amount) {
        return amount + " paid via Google Pay";
    }
}
