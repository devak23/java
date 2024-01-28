package com.ak.reactive.assignment.section02;

import lombok.Getter;

/**
 * @see BrokerService for problem definition.
 */
@Getter
public class Config {
    private final Integer baseStockPrice = 100;

    private final int minPriceSwing = -11;

    private final int maxPriceSwing = 11;

    private final Double minStockPrice = 90.0d;

    private final Double maxStockPrice = 110.0d;

    private final Long duration = 1L;
}
