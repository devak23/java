package com.ak.reactive.assignment.section02;

import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * A StockTicker will emit a particular stock price at a specific frequency (5 seconds).
 */
public class StockTicker {
    @Value("${stock.base.price}=100.0")
    private Double baseStockPrice;

    public static final Integer MIN = -11;
    public static final Integer MAX = 11;


    public Flux<Double> getPriceUpdates() {
        return Flux.interval(Duration.ofSeconds(5)).map(i -> baseStockPrice + priceTurn());
    }

    private double priceTurn() {
        int range = (MIN + MAX) + 1;
        return Math.random() * range + MIN ;
    }
}
