package com.example.streaming.model;

import java.util.List;

public class DataStreamSimulator {
    private int batchNumber = 0;
    private int totalBatches = 5;

    public boolean hasNext() {
        return batchNumber < totalBatches;
    }

    public List<DataRecord> getNextBatch() {
        batchNumber++;

        // Simulate data fetching delay
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return List.of(
                new DataRecord(System.currentTimeMillis(), "ID" + batchNumber + "01", "Description " + batchNumber + ".1", 100.0 * batchNumber),
                new DataRecord(System.currentTimeMillis(), "ID" + batchNumber + "02", "Description " + batchNumber + ".2", 200.0 * batchNumber),
                new DataRecord(System.currentTimeMillis(), "ID" + batchNumber + "03", "Description " + batchNumber + ".3", 300.0 * batchNumber)
        );
    }

    public int getBatchNumber() {
        return batchNumber;
    }
}

