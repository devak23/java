package com.ak.rnd.orderstatemachine.controller;

import com.ak.rnd.orderstatemachine.model.OrderEvents;
import com.ak.rnd.orderstatemachine.model.OrderInvoice;
import com.ak.rnd.orderstatemachine.model.OrderStates;
import com.ak.rnd.orderstatemachine.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/v1/workflow")
@RequiredArgsConstructor
@Slf4j
public class WorkflowController {
    private final OrderRepository orderRepository;
    private final StateMachineFactory<OrderStates, OrderEvents> stateMachineFactory;

    @PutMapping("/change")
    public String changeState(@RequestBody OrderInvoice order) {
        // making the state machine in the current state of the order.

        // Here we can see the build(order) method which is very important. This method aims to get the state machine
        // from state machine factory and set it to a state in which our current order is in. Usually current state is
        // pulled from the database.
        StateMachine<OrderStates, OrderEvents> stateMachine = build(order);
        stateMachine.getExtendedState().getVariables().put("paymentType", order.getPaymentType());
        stateMachine.sendEvent(
                // Spring MessageBuilder is quite useful in sending events as it allows us to attach headers which we
                // have used to send events. In the following steps we would be defining an interceptor where we will
                // be using the header inputs.
                MessageBuilder
                        .withPayload(OrderEvents.valueOf(order.getEvent()))
                        .setHeader("orderId", order.getId())
                        .setHeader("state", order.getState())
                        .build()
        );
        return "Change initiated";
    }

    @GetMapping
    @RequestMapping("/currentState")
    public String state() {
        return stateMachineFactory.getStateMachine().getId();
    }

    // Pulling the state from the database and reset the machine to that state.
    public StateMachine<OrderStates, OrderEvents> build(final OrderInvoice order) {
        var orderFromDb = orderRepository.findById(order.getId()).orElseThrow();
        var stateMachine = stateMachineFactory.getStateMachine(orderFromDb.getId().toString());
        stateMachine.stop();
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    // Once we have reset the machine to existing state with help of db, we just have to send the event
                    // which we have received in the request. But we still need to persist the order state in our db
                    // when the state machine has altered the state of the order.
                    sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<>() {
                        @Override
                        public void preStateChange(State<OrderStates, OrderEvents> state
                                , Message<OrderEvents> message
                                , Transition<OrderStates, OrderEvents> transition
                                , StateMachine<OrderStates, OrderEvents> stateMachine
                                , StateMachine<OrderStates, OrderEvents> rootStateMachine) {

                            var orderId = Long.class.cast(message.getHeaders().get("orderId"));
                            var order = orderRepository.findById(orderId).orElseThrow();
                            order.setState(state.getId().name());
                            orderRepository.save(order);
                            log.info("Order {} saved.", order);
                        }
                    });


                    sma.resetStateMachineReactively(
                            new DefaultStateMachineContext<>(
                                    OrderStates.valueOf(orderFromDb.getState()), null, null, null)
                    );
                });
        stateMachine.start();
        return stateMachine;

        // As, we can see that we have pulled the state machine first from stateMachineFactory which we have injected
        // already, then we have stopped it, then we have reset the machine to a state of OrderInvoice which we have
        // pulled from the database.
    }
}
