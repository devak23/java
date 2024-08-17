package com.ak.rnd.paymentstatemachine.service;

import com.ak.rnd.paymentstatemachine.model.Payment;
import com.ak.rnd.paymentstatemachine.model.PaymentEvent;
import com.ak.rnd.paymentstatemachine.model.PaymentState;
import org.springframework.statemachine.StateMachine;

// Step 7:
public interface PaymentService {
    Payment newPayment(Payment payment);
    StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);
    StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId);
    StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId);
}
