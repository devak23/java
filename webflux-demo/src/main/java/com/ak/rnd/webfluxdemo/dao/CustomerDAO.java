package com.ak.rnd.webfluxdemo.dao;

import com.ak.rnd.webfluxdemo.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Component
@Slf4j
public class CustomerDAO {

    public List<Customer> getCustomersViaBlockingCall() {
        return IntStream.range(1, 51)
                .peek(_ -> sleep(1))
                .mapToObj(i -> Customer.builder()
                        .id((long) i)
                        .name("Customer " + i)
                        .build()
                )
                .toList();
    }

    public Flux<Customer> getCustomersViaNonBlockingCall() {
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
}
