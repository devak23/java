package com.ak.rnd.paymentstatemachine.config;

import com.ak.rnd.paymentstatemachine.model.PaymentEvent;
import com.ak.rnd.paymentstatemachine.model.PaymentState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

// Step 5: Define a StateMachineConfig. Here we configure 3 things: [A] StateConfigurer, [B] TransitionConfigurer and
// [C] ConfigurationConfigurer
@Slf4j
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<PaymentState, PaymentEvent> {

    // [A] StateConfigurer
    @Override
    public void configure(StateMachineStateConfigurer<PaymentState, PaymentEvent> states) throws Exception {
        states.withStates()
                .initial(PaymentState.NEW)
                .states(EnumSet.allOf(PaymentState.class))
                .end(PaymentState.AUTH)
                .end(PaymentState.PRE_AUTH_ERROR)
                .end(PaymentState.AUTH_ERROR)
        ;
    }

    // [B] TransitionConfigurer
    @Override
    public void configure(StateMachineTransitionConfigurer<PaymentState, PaymentEvent> transitions) throws Exception {
        transitions
            .withExternal()
                .source(PaymentState.NEW)
                .target(PaymentState.NEW)
                .event(PaymentEvent.PRE_AUTHORIZE)
            .and()
            .withExternal()
                .source(PaymentState.NEW)
                .target(PaymentState.PRE_AUTH)
                .event(PaymentEvent.PRE_AUTH_APPROVED)
            .and()
            .withExternal()
                .source(PaymentState.NEW)
                .target(PaymentState.PRE_AUTH_ERROR)
                .event(PaymentEvent.AUTH_DECLINED);

    }

    // [C] ConfigurationConfigurer
    @Override
    public void configure(StateMachineConfigurationConfigurer<PaymentState, PaymentEvent> config) throws Exception {
        StateMachineListenerAdapter<PaymentState, PaymentEvent> adapter = new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<PaymentState, PaymentEvent> from, State<PaymentState, PaymentEvent> to) {
                log.info("State Changed from: {}, to {}", from, to);
            }
        };
        config.withConfiguration().listener(adapter);
    }
}
