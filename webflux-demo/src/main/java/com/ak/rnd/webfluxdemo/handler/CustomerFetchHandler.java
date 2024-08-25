package com.ak.rnd.webfluxdemo.handler;

import com.ak.rnd.webfluxdemo.dao.CustomerDAO;
import com.ak.rnd.webfluxdemo.dto.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerFetchHandler {

    private final CustomerDAO customerDAO;

    public Mono<ServerResponse> getCustomers(final ServerRequest request) {
        return ServerResponse.ok().body(customerDAO.getCustomers(), Customer.class);
    }

}
