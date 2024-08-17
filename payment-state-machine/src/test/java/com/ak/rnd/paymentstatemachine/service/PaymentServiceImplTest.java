package com.ak.rnd.paymentstatemachine.service;

import com.ak.rnd.paymentstatemachine.model.Payment;
import com.ak.rnd.paymentstatemachine.model.PaymentEvent;
import com.ak.rnd.paymentstatemachine.model.PaymentState;
import com.ak.rnd.paymentstatemachine.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import java.math.BigDecimal;

@SpringBootTest
@Slf4j
public class PaymentServiceImplTest {
    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentRepository paymentRepository;

    Payment payment;

    @BeforeEach
    void setUp() throws Exception {
        payment = Payment.builder().amount(BigDecimal.TEN).build();
    }

    @Transactional
    @Test
    void testPreAuth() {
        Payment savedPayment = paymentService.newPayment(payment);
        StateMachine<PaymentState, PaymentEvent> sm = paymentService.preAuth(savedPayment.getId());
        Payment preAuthPayment = paymentRepository.getOne(savedPayment.getId());
        log.info("PaymentId = {}", preAuthPayment.getId());
        log.info("PreAuth Payment = {}", preAuthPayment);
    }

}