package com.ak.rnd.paymentstatemachine.actions;

import com.ak.rnd.paymentstatemachine.model.PaymentEvent;
import com.ak.rnd.paymentstatemachine.model.PaymentState;
import com.ak.rnd.paymentstatemachine.service.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.Random;

// Step 11
@Slf4j
public class PreAuthAction implements Action<PaymentState, PaymentEvent> {
    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> stateContext) {
        log.info("PreAuth action is called");

        if (new Random().nextInt(10) < 8) {
            log.info("Approved");
            stateContext.getStateMachine().sendEvent(
                    MessageBuilder
                            .withPayload(PaymentEvent.AUTH_APPROVED)
                            .setHeader(PaymentServiceImpl.PAYMENT_ID_HEADER, stateContext.getMessageHeader(PaymentServiceImpl.PAYMENT_ID_HEADER))
                            .build()
            );
        } else {
            log.info("Declined. No Credit!");
            stateContext.getStateMachine().sendEvent(
                    MessageBuilder
                            .withPayload(PaymentEvent.PRE_AUTH_DECLINED)
                            .setHeader(PaymentServiceImpl.PAYMENT_ID_HEADER, stateContext.getMessageHeader(PaymentServiceImpl.PAYMENT_ID_HEADER))
                            .build()
            );
        }
    }
}
