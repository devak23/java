package com.ak.rnd.webfluxdemo.router;

import com.ak.rnd.webfluxdemo.handler.CustomerFetchHandler;
import com.ak.rnd.webfluxdemo.handler.CustomerFetchStreamHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {
    private final CustomerFetchHandler customerFetchHandler;
    private final CustomerFetchStreamHandler customerFetchStreamHandler;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route()
                .GET("/router/customers", customerFetchHandler::getCustomers)
                .GET("/router/customers/stream", customerFetchStreamHandler::getCustomersStream)
                .GET("/router/customers/{customerId}", customerFetchHandler::getCustomer)
                .POST("/router/customers", customerFetchHandler::saveCustomer)
                .build();
    }
}
