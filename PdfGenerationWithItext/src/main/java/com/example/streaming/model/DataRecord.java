package com.example.streaming.model;

public class DataRecord {
    private String timestamp;
    private String id;
    private String description;
    private double amount;

    public DataRecord(long timestamp, String id, String description, double amount) {
        this.timestamp = String.valueOf(timestamp);
        this.id = id;
        this.description = description;
        this.amount = amount;
    }

    // Getters
    public String getTimestamp() { return timestamp; }
    public String getId() { return id; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
}