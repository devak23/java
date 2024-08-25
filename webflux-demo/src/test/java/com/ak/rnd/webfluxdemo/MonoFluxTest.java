package com.ak.rnd.webfluxdemo;

import com.github.javafaker.BackToTheFuture;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class MonoFluxTest {

    @Test
    public void testMono() {
        Mono<String> publisher = Mono.just("Hello World!").log();
        publisher.subscribe(e -> log.info("{}", e));
    }

    @Test
    public void testMonoWithError() {
        Mono<?> publisher = Mono.just("Mono with Error")
                .then(Mono.error(new RuntimeException("Couldn't fetch data from the database.")))
                .log();
        publisher.subscribe(
                data -> log.info("Received: {}", data)
                , error -> log.error("Producer in error:", error)
        );
    }

    @Test
    public void testFlux() {
        Flux<String> producer = Flux.just("Abhay", "Manik", "Soham")
                .concatWithValues("Suchitra")
                .log();
        producer.subscribe(log::info);
    }

    @Test
    public void testFluxWithError() {
        Flux<?> producer = Flux.just("Abhay", "Manik", "Soham")
                .concatWith(Flux.error(new RuntimeException("Error reading file.")));
        producer.subscribe(
                data -> log.info("Received: {}", data)
                , error -> log.error("Producer in error:", error)
        );
    }

    @Test
    public void testFluxWithErrorAndConcatValues() {
        Flux<?> producer = Flux.just("Abhay", "Manik", "Soham")
                .concatWith(Flux.error(new RuntimeException("Error reading file.")))
                .concatWithValues("Suchitra");
        producer.subscribe(
                data -> log.info("Received: {}", data)
                , error -> log.error("Producer in error:", error)
        );
    }
}
