package com.ak.rnd.webfluxdemo.handler;

import com.ak.rnd.webfluxdemo.dao.CustomerDAO;
import com.ak.rnd.webfluxdemo.dto.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerFetchHandler {

    private final CustomerDAO customerDAO;

    public Mono<ServerResponse> getCustomers(final ServerRequest request) {
        return ServerResponse.ok().body(customerDAO.getCustomers(), Customer.class);
    }

    public Mono<ServerResponse> getCustomer(final ServerRequest request) {
        long customerId = Long.parseLong(request.pathVariable("customerId"));
        log.info("Get customer with id {}", customerId);
        return ServerResponse.ok().body(customerDAO.getCustomerImperative(customerId), Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        Mono<String> result = customerDAO.saveCustomer(customerMono);
        return ServerResponse.ok().body(result, String.class);
    }
}
