package com.ak.rnd.turnstilestatemachine.config;

import com.ak.rnd.turnstilestatemachine.model.TurnstileEvent;
import com.ak.rnd.turnstilestatemachine.model.TurnstileState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;

@Slf4j
@Configuration
@EnableStateMachine
public class TurnstileMachineConfig {


    @Bean
    public StateMachine<TurnstileState, TurnstileEvent> stateMachine(
            StateMachineListener<TurnstileState, TurnstileEvent> listener) throws Exception {

        StateMachineBuilder.Builder<TurnstileState, TurnstileEvent> builder = StateMachineBuilder.builder();

        builder.configureStates().withStates()
                .initial(TurnstileState.LOCKED)
                .states(EnumSet.allOf(TurnstileState.class));

        builder.configureTransitions()
            .withExternal()
                .source(TurnstileState.LOCKED)
                .target(TurnstileState.UNLOCKED)
                .event(TurnstileEvent.PUSH_COIN)
            .and()
            .withExternal()
                .source(TurnstileState.UNLOCKED)
                .target(TurnstileState.LOCKED)
                .event(TurnstileEvent.PUSH_LEVER);

        StateMachine<TurnstileState, TurnstileEvent> stateMachine = builder.build();
        stateMachine.addStateListener(listener);

        stateMachine.getStateMachineAccessor().doWithAllRegions(
                sma -> sma.resetStateMachine(
                        new DefaultStateMachineContext<>(
                                TurnstileState.LOCKED, null, null, null)));
        return stateMachine;
    }

    @Bean
    public StateMachineListener<TurnstileState, TurnstileEvent> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<TurnstileState, TurnstileEvent> from, State<TurnstileState, TurnstileEvent> to) {
                log.info("State changed from: {} to: {}", from.getId(), to.getId());
            }
        };
    }
}
