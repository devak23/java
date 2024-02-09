package com.ak.reactive.lectures.section05.assignment;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RevenueService {

    private Map<String, Double> db = new HashMap<>(10);

    public RevenueService() {
        db.put("Kids", 0.0d);
        db.put("Automotive", 0.0d);
    }

    public Consumer<PurchaseOrder> subscribeOrderStream() {
        return purchaseOrder -> db.computeIfPresent(purchaseOrder.getCategory(), (k,v) ->  v + purchaseOrder.getPrice());
    }

    public Flux<String> revenueStream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> db.toString());
    }
}
