package com.ak.rnd.jsonsurferexamples.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionDataClient {
    private final Random random = new Random();
    private final String[] banks = {"HDFC Bank", "Axis Bank", "ICICI Bank"};

    /**
     * Simulates streaming data from a third-party service.
     * Returns a Stream that generates data on-demand (lazy evaluation).
     * This ensures we don't load all records into memory at once.
     */
    public Stream<Map<String, String>> streamTransactionData(String requestId) {
        log.info("Starting to stream transaction data for requestId: {}", requestId);

        // Simulate a large dataset - generate 5000 records
        int totalRecords = 5000;

        return Stream.generate(() -> generateMockTransaction())
                .limit(totalRecords)
                .peek(record -> {
                    // Simulate network delay for realistic streaming behavior
                    try {
                        Thread.sleep(1); // 1ms per record
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
    }

    private Map<String, String> generateMockTransaction() {
        Map<String, String> transaction = new HashMap<>();

        transaction.put("transactionRef", generateTransactionRef());
        transaction.put("amount", String.format("%.2f", random.nextDouble() * 100000));
        transaction.put("bank", banks[random.nextInt(banks.length)]);
        transaction.put("currency", "INR");
        transaction.put("branchCode", String.format("BR%04d", random.nextInt(9999)));

        return transaction;
    }

    private String generateTransactionRef() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder ref = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            ref.append(chars.charAt(random.nextInt(chars.length())));
        }
        return ref.toString();
    }
}
