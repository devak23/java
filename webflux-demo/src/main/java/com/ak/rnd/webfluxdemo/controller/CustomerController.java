package com.ak.rnd.webfluxdemo.controller;

import com.ak.rnd.webfluxdemo.dto.Customer;
import com.ak.rnd.webfluxdemo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    // It's this produces = MediaType.TEXT_EVENT_STREAM_VALUE that enables the data to flow to the browser instantly
    // Remove this and you will get back the traditional way of getting the list of customers.
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> getCustomerStream() {
        return customerService.getAllCustomersStream();
    }
}
