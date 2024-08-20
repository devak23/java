package com.ak.rnd.orderstatemachine.controller;

import com.ak.rnd.orderstatemachine.model.OrderInvoice;
import com.ak.rnd.orderstatemachine.model.OrderStates;
import com.ak.rnd.orderstatemachine.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/createOrder")
    public OrderInvoice createOrder() {
        OrderInvoice orderInvoice = new OrderInvoice();
        orderInvoice.setState(OrderStates.SUBMITTED.name());
        orderInvoice.setOrderDate(LocalDate.now());
        return orderRepository.save(orderInvoice);
    }
}
