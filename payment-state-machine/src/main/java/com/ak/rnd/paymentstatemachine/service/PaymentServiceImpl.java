package com.ak.rnd.paymentstatemachine.service;

import com.ak.rnd.paymentstatemachine.model.Payment;
import com.ak.rnd.paymentstatemachine.model.PaymentEvent;
import com.ak.rnd.paymentstatemachine.model.PaymentState;
import com.ak.rnd.paymentstatemachine.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

// Step 8
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    public static final String PAYMENT_ID_HEADER = "payment_id";

    private final PaymentRepository paymentRepository;
    private final StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;
    private final PaymentStateChangeInterceptor paymentStateChangeInterceptor;

    @Override
    public Payment newPayment(Payment payment) {
        payment.setPaymentState(PaymentState.NEW);
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId) {
        StateMachine<PaymentState, PaymentEvent> stateMachine = build(paymentId);
        sendEvent(paymentId, stateMachine, PaymentEvent.PRE_AUTH_APPROVED);
        return stateMachine;
    }

    @Override
    @Transactional
    public StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId) {
        StateMachine<PaymentState, PaymentEvent> stateMachine = build(paymentId);
        sendEvent(paymentId, stateMachine, PaymentEvent.AUTHORIZE);
        return stateMachine;
    }

    @Override
    @Transactional
    public StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId) {
        StateMachine<PaymentState, PaymentEvent> stateMachine = build(paymentId);
        sendEvent(paymentId, stateMachine, PaymentEvent.AUTH_DECLINED);
        return stateMachine;
    }

    private void sendEvent(Long paymentId, StateMachine<PaymentState, PaymentEvent> stateMachine, PaymentEvent paymentEvent) {
        Message<PaymentEvent> message = MessageBuilder
                .withPayload(paymentEvent)
                .setHeader(PAYMENT_ID_HEADER, paymentId)
                .build();
        stateMachine.sendEvent(message);
    }

    private StateMachine<PaymentState, PaymentEvent> build(Long paymentId) {
        Payment payment = paymentRepository.getReferenceById(paymentId);

        StateMachine<PaymentState, PaymentEvent> stateMachine =
                stateMachineFactory.getStateMachine(Long.toString(payment.getId()));
        stateMachine.stop();

        stateMachine.getStateMachineAccessor().doWithAllRegions(sma -> {
            sma.addStateMachineInterceptor(paymentStateChangeInterceptor);
            sma.resetStateMachine(new DefaultStateMachineContext(payment.getPaymentState(), null, null, null));
        });
        stateMachine.start();

        return stateMachine;
    }
}
