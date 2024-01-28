package com.ak.reactive.assignment.section02;

import com.ak.reactive.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @see BrokerService for problem definition.
 * A StockTicker will emit a particular stock price at a specific frequency.
 */
public class StockTicker {
    private final Config config = new Config();


    public Flux<Double> getPriceUpdates() {
        AtomicInteger atomicInteger = new AtomicInteger(config.getBaseStockPrice());
        return Flux.interval(Duration.ofSeconds(config.getDuration()))
                .map(i -> (double) atomicInteger.accumulateAndGet(
                        Util.faker().random().nextInt(-5, 5),
                        (a, b) -> a + b
                ));
    }
}
