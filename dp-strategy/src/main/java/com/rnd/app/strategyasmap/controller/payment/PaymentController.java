package com.rnd.app.strategyasmap.controller.payment;

import com.rnd.app.strategyasmap.model.payment.PaymentMode;
import com.rnd.app.strategyasmap.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{type}/{amount}")
    public String makePayment(@PathVariable final String type, @PathVariable final double amount) {
        return paymentService.processPayment(PaymentMode.valueOf(type.toUpperCase()), amount);
    }
}
