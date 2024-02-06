package com.ak.reactive.lectures.section04.helper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private static final Map<Integer, List<PurchaseOrder>> db = new HashMap<>();

    static {
        List<PurchaseOrder> orders1 = Arrays.asList(new PurchaseOrder(1), new PurchaseOrder(1), new PurchaseOrder(1));
        db.put(1, orders1);
        List<PurchaseOrder> orders2 = Arrays.asList(new PurchaseOrder(2), new PurchaseOrder(2));
        db.put(2, orders2);
    }

    public static Flux<PurchaseOrder> getOrdersFor(int customerId) {
        return Flux.create((FluxSink<PurchaseOrder> purchaseOrderFluxSink) -> {
           db.get(customerId).forEach(purchaseOrderFluxSink::next);
           purchaseOrderFluxSink.complete();
        }).delayElements(Duration.ofSeconds(1));
    }
}
