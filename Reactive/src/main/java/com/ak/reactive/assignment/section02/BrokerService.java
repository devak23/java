package com.ak.reactive.assignment.section02;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Problem: We are required to have a producer and consumer (possibly more than 1) Consumers will cancel subscription
 * to the producer if the stock price rises above 110 or falls below 90. Stock price to be emitted at a specific
 * frequency of 1 second
 *
 * This class will listen to the StockTicker. The broker will have an option to cancel the subscription to the ticker
 * if the stock price rises above a certain level or falls below a certain level. All of this needs to be configured
 * in a properties file called broker-ticker.properties
 */

public class BrokerService {

    private final CountDownLatch latch;
    private StockTicker ticker;
    private AtomicReference<Subscription> subsRef;
    private Config config;

    public BrokerService() {
        ticker = new StockTicker();
        subsRef = new AtomicReference<>();
        config = new Config();
        latch = new CountDownLatch(1);
    }

    public void init() {
        ticker.getPriceUpdates()
                .subscribeWith(new Subscriber<Double>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subsRef.set(subscription);
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Double price) {
                        System.out.println("Received: " + LocalDateTime.now() + ", Price = " + price);
                        if (price > config.getMaxStockPrice() || price < config.getMinStockPrice()) {
                            System.out.println("Price broke the barrier price. Cancelling subscription.");
                            subsRef.get().cancel();
                            latch.countDown();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("ERROR: " + throwable.getMessage());
                        latch.countDown();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Operation Completed.");
                        latch.countDown();
                    }
                });
    }

    public void await() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        BrokerService bs = new BrokerService();
        bs.init();
        bs.await();
    }
}
