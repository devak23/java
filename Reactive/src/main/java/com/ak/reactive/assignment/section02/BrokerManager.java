package com.ak.reactive.assignment.section02;

import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

/**
 * This class will listen to the StockTicker. The broker will have an option to cancel the subscription to the ticker
 * if the stock price rises above a certain level or falls below a certain level. All of this needs to be configured
 * in a properties file called broker-ticker.properties
 */

public class BrokerManager {

    private StockTicker ticker;
    private Broker zerodha;
    private Broker shareKhan;
    private Broker oswald;

    public BrokerManager() {
        ticker = new StockTicker();
        zerodha = Broker.builder().name("Zerodha").subscribed(true).build();
        shareKhan = Broker.builder().name("Share Khan").subscribed(true).build();
        oswald = Broker.builder().name("Motilal Oswald").subscribed(true).build();
    }

    public void init() {
        Flux<Double> priceUpdates = ticker.getPriceUpdates();
    }


    public static void main(String[] args) {
        BrokerManager bm = new BrokerManager();
        bm.init();
    }

}
