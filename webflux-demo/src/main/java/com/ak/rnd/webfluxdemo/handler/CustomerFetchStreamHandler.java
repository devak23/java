package com.ak.rnd.webfluxdemo.handler;

import com.ak.rnd.webfluxdemo.dao.CustomerDAO;
import com.ak.rnd.webfluxdemo.dto.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerFetchStreamHandler {
    private final CustomerDAO customerDAO;

    public Mono<ServerResponse> getCustomersStream(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customerDAO.getCustomersViaNonBlockingCall(), Customer.class);
    }
}
