package com.ak.reactive.assignment.section02;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class Broker implements Subscriber<Double> {
    // We also need the atomic reference is to keep a track of the subscription reference when we create our own
    // subscriber
    private final AtomicReference<Subscription> subsRef;

    // We need the configuration class to read all the values from properties file.
    private final Config config;

    // Finally, when we execute the main method in this class, it will terminate immediately. The countdown latch here
    // will keep the main thread waiting till our broker's conditions are satisfied. This eliminates the need of
    // Util.sleep() the way we used in other examples.
    private final CountDownLatch latch;


    public Broker() {
        subsRef = new AtomicReference<>();
        config = new Config();
        latch = new CountDownLatch(1);
    }



    @Override
    public void onSubscribe(Subscription subscription) {
        // As usual, onSubscribe will store the reference of the subscription, so we can cancel it later.
        subsRef.set(subscription);
        // It will also request for ALL possible values that the Ticker emits.
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Double price) {
        System.out.println("Received: " + LocalDateTime.now() + ", Price = " + price);

        // This is where our business logic gets triggered. If the stock price rises or falls above and below certain
        // configured values (respectively), the broker will stop listening to the ticker updates by calling the
        // cancel method on the subscription. It will then `count down` the latch from 1 to 0, thereby signaling the
        // client that it was done with its job.
        if (price > config.getMaxStockPrice() || price < config.getMinStockPrice()) {
            System.out.println("Price broke the barrier price. Cancelling subscription.");
            subsRef.get().cancel();
            latch.countDown();
        }
    }

    // any error received by the broker will also trigger the count-down of the latch
    @Override
    public void onError(Throwable throwable) {
        System.out.println("ERROR: " + throwable.getMessage());
        latch.countDown();
    }


    // and similarly, on completion too, the count-down latch is triggered.
    @Override
    public void onComplete() {
        System.out.println("Operation Completed.");
        latch.countDown();
    }

    // this method provides a way to the caller program (Main program in our case) to wait till the count-down latch is
    // not set to Zero
    public void await() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
