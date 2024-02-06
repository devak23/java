package com.ak.reactive.lectures.section04.helper;

import reactor.core.publisher.Flux;

public class CustomerService {

    public static Flux<Customer> getCustomers() {
        return Flux.range(1, 2)
                .map(Customer::new);
    }
}
