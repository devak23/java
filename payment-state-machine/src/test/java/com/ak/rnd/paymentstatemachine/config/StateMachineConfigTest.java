package com.ak.rnd.paymentstatemachine.config;

import com.ak.rnd.paymentstatemachine.model.PaymentEvent;
import com.ak.rnd.paymentstatemachine.model.PaymentState;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.UUID;

@SpringBootTest
@Slf4j
public class StateMachineConfigTest {

    @Autowired
    StateMachineFactory<PaymentState, PaymentEvent> sf;

    @Test
    public void testNewStateMachine() {
        final StateMachine<PaymentState, PaymentEvent> sm = sf.getStateMachine(UUID.randomUUID());
        sm.start();
        assertPaymentState(sm);

        sm.sendEvent(PaymentEvent.PRE_AUTHORIZE);
        assertPaymentState(sm);

        sm.sendEvent(PaymentEvent.AUTH_APPROVED);
        assertPaymentState(sm);

        sm.sendEvent(PaymentEvent.PRE_AUTH_DECLINED);
        assertPaymentState(sm);
    }

    private static void assertPaymentState(StateMachine<PaymentState, PaymentEvent> sm) {
        Assertions.assertThat(sm.getState().getIds())
                .isNotNull()
                .isNotEmpty()
                .element(0)
                .isEqualTo(PaymentState.NEW);
    }
}