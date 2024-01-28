package com.ak.reactive.assignment.section02;

/**
 * Problem: We are required to have a producer and consumer (possibly more than 1) Consumers will cancel subscription
 * to the producer if the stock price rises above 110 or falls below 90. Stock price to be emitted at a specific
 * frequency of 1 second
 * <p>
 * This class just manages the StockTicker and the broker. It registers the Broker with the ticker and waits till
 * the program gets terminated.
 */

public class BrokerService {
    // So we need the StockTicker class to provide us with the price updates
    private final StockTicker ticker;

    // The broker is an implementation of the Subscriber<Double> that will listen to our updates.
    private final Broker broker;


    // We initialize the services. Ordinarily, we will auto-wire the services here.
    public BrokerService() {
        ticker = new StockTicker();
        broker = new Broker();
    }

    // The init gets triggered from the main.
    public void init() {

        // Ticker produces the updates...
        ticker.getPriceUpdates()
                // and we subscribe our own class (Broker) to listen to the price updates
                .subscribeWith(broker);

        // The await basically causes `await()` to be called on the countdown latch defined in the Broker.
        broker.await();
    }


    // The Main function.
    public static void main(String[] args) {
        BrokerService bs = new BrokerService();
        bs.init();
    }
}
