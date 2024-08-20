package com.ak.rnd.orderstatemachine.config;

import com.ak.rnd.orderstatemachine.model.OrderEvents;
import com.ak.rnd.orderstatemachine.model.OrderStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Slf4j
@Configuration
@EnableStateMachineFactory
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStates, OrderEvents> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {
        super.configure(config);
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
        states.withStates()
                .initial(OrderStates.SUBMITTED)
                .state(OrderStates.PAID)
                .end(OrderStates.FULLFILLED)
                .end(OrderStates.CANCELLED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
        transitions
            .withExternal()
                .source(OrderStates.SUBMITTED)
                .event(OrderEvents.PAY)
                .target(OrderStates.PAID)
                // Here the Guard, is used to make a decision whether we want to block the state from being changed.
                // Guard is a functional interface (and hence the lambda) with the method which returns true or false.
                // True means the state will change and false means the state will not change.
                .guard(ctx -> {
                   log.info("true -> stateChanged, false -> state not changed");
                   var paymentType = String.class.cast(ctx.getExtendedState().getVariables().get("paymentType"));
                   return !"cod".equalsIgnoreCase(paymentType);
                })
            .and()
            .withExternal()
                .source(OrderStates.PAID)
                .event(OrderEvents.FULFILL)
                .target(OrderStates.FULLFILLED)
                // We can define action as a separate bean and then call it directly from action. Action is a functional
                // interface and hence we have used lambda for its implementation. It’s just taking a consumer.
                .action(ctx -> {
                    log.info("This is a PAID (fullfilled) handler where we can perform some logging");
                })
            .and()
            .withExternal()
                .source(OrderStates.SUBMITTED)
                .event(OrderEvents.CANCEL)
                .target(OrderStates.CANCELLED)
                .action(ctx -> {
                    log.info("This is a SUBMITTED handler where we can perform some logging");
                })
            .and()
            .withExternal()
                .source(OrderStates.PAID)
                .event(OrderEvents.CANCEL)
                .target(OrderStates.CANCELLED)
                .action(ctx -> {
                    log.info("This is a PAID (cancelled) handler where we can perform some logging");
                });

    }
}
