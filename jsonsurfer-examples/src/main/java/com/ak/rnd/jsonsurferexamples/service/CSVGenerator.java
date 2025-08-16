package com.ak.rnd.jsonsurferexamples.service;

import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.jsfr.json.JsonSurfer;
import org.jsfr.json.JsonSurferJackson;
import org.jsfr.json.SurfingConfiguration;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

@Slf4j
@Service("csvGenerator")
public class CSVGenerator implements Generator {

    @Override
    public void generate(Supplier<InputStream> inputStreamSupplier, OutputStream outputStream) throws Exception {
        long startTime = System.currentTimeMillis();
        JsonSurfer surfer = JsonSurferJackson.INSTANCE;
        
        // Memory-efficient CSV writer with buffered output for proper line breaks
        java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(writer);
        CSVWriter csvWriter = new CSVWriter(
            bufferedWriter,
            CSVWriter.DEFAULT_SEPARATOR,
            CSVWriter.DEFAULT_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            System.lineSeparator()  // Use system line separator
        );
        
        // Track columns and row count
        List<String> columns = new ArrayList<>();
        AtomicReference<Boolean> headerWritten = new AtomicReference<>(false);
        AtomicInteger rowCount = new AtomicInteger(0);
        
        try {
            // First pass: Process headerInfo
            try (InputStream inputStream = inputStreamSupplier.get()) {
                processHeaderInfo(inputStream, csvWriter, surfer);
            }
            
            // Second pass: Process configInfo and entityInfo side by side
            try (InputStream inputStream = inputStreamSupplier.get()) {
                processConfigAndEntityInfo(inputStream, csvWriter, surfer);
            }
            
            // Third pass: collect columns
            try (InputStream inputStream = inputStreamSupplier.get()) {
                collectColumns(inputStream, csvWriter, surfer, columns, headerWritten);
            }
            
            // Fourth pass: process rows
            try (InputStream inputStream = inputStreamSupplier.get()) {
                processDataRows(inputStream, csvWriter, surfer, columns, rowCount);
            }
            
            // Final flush and close
            csvWriter.flush();
            csvWriter.close();
            
            long totalTime = System.currentTimeMillis() - startTime;
            log.info("CSV streaming complete - Total rows: {}, Total time: {}ms, Memory efficient: true", 
                    rowCount.get(), totalTime);
                    
        } catch (Exception e) {
            log.error("Error during CSV streaming: " + e.getMessage(), e);
            throw new RuntimeException("CSV generation failed", e);
        }
    }
    
    private void processHeaderInfo(InputStream jsonInputStream, CSVWriter csvWriter, JsonSurfer surfer) throws Exception {
        log.info("Processing headerInfo...");
        
        SurfingConfiguration headerConfig = SurfingConfiguration.builder()
                .bind("$.headerInfo.rows", (value, context) -> {
                    try {
                        log.info("Found headerInfo rows: {}", value.getClass().getSimpleName());
                        
                        com.fasterxml.jackson.databind.node.ObjectNode headerNode = 
                            (com.fasterxml.jackson.databind.node.ObjectNode) value;
                        
                        // Process each key-value pair in headerInfo.rows
                        headerNode.fields().forEachRemaining(entry -> {
                            String key = entry.getKey();
                            String val = entry.getValue().asText();
                            String headerLine = key + ": " + val;
                            
                            // Write as single column CSV row
                            csvWriter.writeNext(new String[]{headerLine});
                            log.info("Written header line: {}", headerLine);
                        });
                        
                        // Add 2 blank lines after header info
                        for (int i = 0; i < 2; i++) {
                            csvWriter.writeNext(new String[]{""});
                        }
                        csvWriter.flush();
                        log.info("Header info and blank lines written");
                        
                    } catch (Exception e) {
                        log.error("Error processing headerInfo: " + e.getMessage(), e);
                    }
                })
                .build();
        
        surfer.surf(jsonInputStream, headerConfig);
    }
    
    private void processConfigAndEntityInfo(InputStream jsonInputStream, CSVWriter csvWriter, JsonSurfer surfer) throws Exception {
        log.info("Processing configInfo and entityInfo...");
        
        List<String> configRows = new ArrayList<>();
        List<String> entityRows = new ArrayList<>();
        
        SurfingConfiguration configEntityConfig = SurfingConfiguration.builder()
                .bind("$.configInfo.rows", (value, context) -> {
                    try {
                        log.info("Found configInfo rows: {}", value.getClass().getSimpleName());
                        
                        com.fasterxml.jackson.databind.node.ObjectNode configNode = 
                            (com.fasterxml.jackson.databind.node.ObjectNode) value;
                        
                        // Process each key-value pair in configInfo.rows
                        configNode.fields().forEachRemaining(entry -> {
                            String key = entry.getKey();
                            String val = entry.getValue().asText();
                            String configLine = key + ": " + val;
                            configRows.add(configLine);
                            log.info("Added config line: {}", configLine);
                        });
                        
                    } catch (Exception e) {
                        log.error("Error processing configInfo: " + e.getMessage(), e);
                    }
                })
                .bind("$.entityInfo.rows", (value, context) -> {
                    try {
                        log.info("Found entityInfo rows: {}", value.getClass().getSimpleName());
                        
                        com.fasterxml.jackson.databind.node.ObjectNode entityNode = 
                            (com.fasterxml.jackson.databind.node.ObjectNode) value;
                        
                        // Process each key-value pair in entityInfo.rows
                        entityNode.fields().forEachRemaining(entry -> {
                            String key = entry.getKey();
                            String val = entry.getValue().asText();
                            String entityLine = key + ": " + val;
                            entityRows.add(entityLine);
                            log.info("Added entity line: {}", entityLine);
                        });
                        
                    } catch (Exception e) {
                        log.error("Error processing entityInfo: " + e.getMessage(), e);
                    }
                })
                .build();
        
        surfer.surf(jsonInputStream, configEntityConfig);
        
        // Write configInfo and entityInfo side by side
        int maxRows = Math.max(configRows.size(), entityRows.size());
        for (int i = 0; i < maxRows; i++) {
            String configValue = i < configRows.size() ? configRows.get(i) : "";
            String entityValue = i < entityRows.size() ? entityRows.get(i) : "";
            
            // Write side by side with 3 empty columns in between
            csvWriter.writeNext(new String[]{configValue, "", "", "", entityValue});
            log.info("Written side-by-side row {}: '{}' | '{}'", i + 1, configValue, entityValue);
        }
        
        // Add 2 blank lines after config and entity info
        for (int i = 0; i < 2; i++) {
            csvWriter.writeNext(new String[]{""});
        }
        
        csvWriter.flush();
        log.info("Config and entity info written side by side");
    }
    
    private void collectColumns(InputStream jsonInputStream, CSVWriter csvWriter, JsonSurfer surfer, 
                               List<String> columns, AtomicReference<Boolean> headerWritten) throws Exception {
        
        SurfingConfiguration columnsConfig = SurfingConfiguration.builder()
                .bind("$.gridInfo.columns[*]", (value, context) -> {
                    // Remove extra quotes if present - value.toString() adds quotes around strings
                    String columnName = value.toString();
                    if (columnName.startsWith("\"") && columnName.endsWith("\"")) {
                        columnName = columnName.substring(1, columnName.length() - 1);
                    }
                    columns.add(columnName);
                    log.info("Found column: {}", columnName);
                })
                .build();
        
        // Process to get columns
        surfer.surf(jsonInputStream, columnsConfig);
        
        // Write grid header now that we have columns
        if (!columns.isEmpty()) {
            String[] headerArray = columns.toArray(new String[0]);
            csvWriter.writeNext(headerArray);
            csvWriter.flush();
            headerWritten.set(true);
            log.info("CSV grid header written with {} columns: {}", columns.size(), columns);
        }
    }
    
    private void processDataRows(InputStream jsonInputStream, CSVWriter csvWriter, JsonSurfer surfer, 
                                List<String> columns, AtomicInteger rowCount) throws Exception {
        log.info("Starting row processing phase...");
        
        SurfingConfiguration rowsConfig = SurfingConfiguration.builder()
                .bind("$.gridInfo.rows[*]", (value, context) -> {
                    try {
                        log.info("ROW FOUND! Processing row data: {}", value.getClass().getSimpleName());
                        log.info("Raw row value: {}", value.toString());
                        
                        // The value is a Jackson ObjectNode, need to extract data properly
                        com.fasterxml.jackson.databind.node.ObjectNode rowNode = 
                            (com.fasterxml.jackson.databind.node.ObjectNode) value;
                        
                        // Create row data array in the same order as columns
                        String[] rowData = new String[columns.size()];
                        log.info("Processing row with {} columns", columns.size());
                        
                        for (int i = 0; i < columns.size(); i++) {
                            String columnName = columns.get(i);
                            com.fasterxml.jackson.databind.JsonNode fieldNode = rowNode.get(columnName);
                            String fieldValue = fieldNode != null && !fieldNode.isNull() ? fieldNode.asText() : "";
                            rowData[i] = fieldValue;
                            log.info("Column '{}' = '{}'", columnName, fieldValue);
                        }
                        
                        csvWriter.writeNext(rowData);
                        csvWriter.flush(); // Flush immediately for debugging
                        
                        int currentCount = rowCount.incrementAndGet();
                        log.info("Successfully processed and wrote row {}", currentCount);
                        
                    } catch (Exception e) {
                        log.error("Error processing row: " + e.getMessage(), e);
                    }
                })
                .build();
        
        log.info("About to start surfing for rows...");
        // Stream process the JSON file for rows
        surfer.surf(jsonInputStream, rowsConfig);
        log.info("Finished surfing for rows. Total rows processed: {}", rowCount.get());
    }
}