package com.ak.rnd.webfluxdemo.dao;

import com.ak.rnd.webfluxdemo.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Component
@Slf4j
public class CustomerDAO {
    private List<Customer> savedCustomers = new ArrayList<>();

    public List<Customer> getCustomersImperative() {
        return IntStream.range(1, 51)
                .peek(_ -> sleep(1))
                .mapToObj(i -> Customer.builder()
                        .id((long) i)
                        .name("Customer " + i)
                        .build()
                )
                .toList();
    }

    private List<Customer> getCustomersImperativeWithoutDelay() {
        return IntStream.range(1, 51)
                .mapToObj(i -> Customer.builder()
                        .id((long) i)
                        .name("Customer " + i)
                        .build()
                )
                .toList();
    }

    public Flux<Customer> getCustomersReactive() {
        return Flux.range(1, 50)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> log.info("Processing element: {}", i))
                .map(i -> Customer.builder().id(Long.valueOf(i)).name("Customer " + i).build());
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    //TODO: change this to a proper list of customers
    public Flux<Customer> getCustomers() {
        return Flux.range(1, 50)
                .map(i -> Customer.builder().id(Long.valueOf(i)).name("Customer " + i).build());
    }

    public Mono<Customer> getCustomerImperative(long customerId) {
        return Mono.justOrEmpty(getCustomersImperativeWithoutDelay()
                .stream()
                .filter(c -> c.getId() == customerId)
                .findFirst());
    }

    public Mono<String> saveCustomer(Mono<Customer> customerMono) {
        return customerMono.map(dto -> savedCustomers.add(
                Customer.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .build()))
                .log()
                .map(b -> b ? "Saved" : "Not Saved");
    }
}
