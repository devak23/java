package com.ak.rnd.jsonsurferexamples.controller;

import lombok.extern.slf4j.Slf4j;
import org.jsfr.json.JsonSurfer;
import org.jsfr.json.JsonSurferGson;
import org.jsfr.json.SurfingConfiguration;
import org.jsfr.json.exception.JsonSurfingException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequestMapping("/api/v1/surfer")
public class SurferController {

    @GetMapping("/")
    public String hello() throws IOException {
        ClassPathResource resource = new ClassPathResource("index.html");
        return Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
    }

    @GetMapping("/basic")
    public String basic() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/basic-1.json");
        String json = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);

        JsonSurfer surfer = JsonSurferGson.INSTANCE;
        
        // Extract the name field
        Object name = surfer.collectOne(json, "$.name");
        log.info("Name: " + name);

        Object age = surfer.collectOne(json, "$.age");
        log.info("Age: " + age);
        
        return "Name: " + name + ", Age: " + age;

        // Key concepts:
        //JsonSurferGson.INSTANCE - Using Gson as the JSON provider
        //collectOne() - Extracts a single value matching the JsonPath
        //$.name - JsonPath expression to select the "name" field
    }
    
    @GetMapping("/basic-arrays")
    public Map<String, Object> basicArrays() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/basic-arrays-1.json");
        String json = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);

        JsonSurfer surfer = JsonSurferGson.INSTANCE;
        
        // Extract all names from 'users' array
        Collection<Object> names = surfer.collectAll(json, "$.users[*].name");
        log.info("Names: " + names);
        
        // Extract 1st user's name
        Object firstName = surfer.collectOne(json, "$.users[0].name");
        log.info("First Name: " + firstName);

        // extract ages greather than 28
        Collection<Object> olderUsers = surfer.collectAll(json, "$.users[?(@.age > 28)].name");
        log.info("Older Users: " + olderUsers);
        
        
        return Map.of("names", names, "firstName", firstName, "olderUsers", olderUsers);


        // Key concepts:
        //collectAll() - Extracts all matching values
        //$..users[*].name - Selects all name fields from users array
        //$.users[0].name - Selects specific array index
        //$.users[?(@.age > 28)] - Filter expression using conditions

    }
    
    @GetMapping("/event-driven-processing")
    public Map<String, Object> eventDrivenProcessing() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/event-driven-processing.json");
        String json = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
        List<String> products = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
     
        JsonSurfer surfer = JsonSurferGson.INSTANCE;

        SurfingConfiguration config = SurfingConfiguration.builder()
                .bind("$.products[*].name", (value, context) -> products.add(value.toString()))
                .bind("$.products[*].price", (value,context) -> prices.add(Double.parseDouble(value.toString())))
                .build();
        
        surfer.surf(json, config);
        return Map.of("products", products, "prices", prices);

        // Key concepts:
        //SurfingConfiguration.builder() - Configure event handlers
        //.bind() - Bind JsonPath to callback function
        //surf() - Process JSON with streaming approach
    }
    
    @GetMapping("/complex-data-structure")
    public Map<String, Object> complexDataStructure() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/complex-data-structure.json");
        String json = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
        Map<String, Object> result = new HashMap<>();
        JsonSurfer surfer = JsonSurferGson.INSTANCE;

        // Get the company name
        result.put("companyName", surfer.collectOne(json, "$.company.name"));

        // Get all the departments
        result.put("departments", surfer.collectAll(json, "$.company.departments[*].name"));

        // Get all employees across all departments
        result.put("employees", surfer.collectAll(json, "$.company.departments[*].employees[*].name"));

        // Get all skills flattened
        result.put("skills", surfer.collectAll(json,"$.company.departments[*].employees[*].skills[*]"));

        // Find employees in the engineering department
        result.put("engineeringEmployees", surfer.collectAll(json, "$.company.departments[?(@.name == 'Engineering')].employees[*].name"));

        return result;
    }
    
    
    @GetMapping("/state-management")
    public Map<String, Object> stateManagement() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/state-management.json");
        String json = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
        JsonSurfer surfer = JsonSurferGson.INSTANCE;

        // State management for aggregating data
        Map<String, Double> regionTotals = new HashMap<>();
        AtomicInteger recordCount = new AtomicInteger(0);

        SurfingConfiguration config = SurfingConfiguration.builder()
                .bind("$.sales[*]", (value, context) -> {
                    recordCount.incrementAndGet();

                    // Extract region and amount from the current sale record
                    Object regionObject = surfer.collectOne(value.toString(), "$.region");
                    Object amountObject = surfer.collectOne(value.toString(), "$.amount");

                    if (regionObject != null && amountObject != null) {
                        String region = regionObject.toString();
                        double amount = Double.parseDouble(amountObject.toString());
                        regionTotals.merge(region, amount, Double::sum);
                        log.info("Processed sale region: " + region + ", amount: " + amount);
                    }
                })
                .build();
        surfer.surf(json, config);

        return Map.of("regionTotals", regionTotals, "recordCount", recordCount.get());
    }

    @GetMapping("/error-handling")
    public Map<String, List<String>> errorHandling() {
        String validJson = "{\"data\":{\"value\":42}}";
        String invalidJson = "{\"data\":{\"value\":}"; // Invalid JSON
        String missingFieldJson = "{\"other\":\"field\"}";

        JsonSurfer surfer = JsonSurferGson.INSTANCE;
        Map<String, List<String>> errorMap = new HashMap<>();
        List<String> errors = new ArrayList<>();
        errorMap.put("ERRORS", errors);

        errors.add(processJsonSafely(surfer, validJson, "$.data.value"));
        errors.add(processJsonSafely(surfer, invalidJson, "$.data.value"));
        errors.add(processJsonSafely(surfer, missingFieldJson, "$.data.value"));

        return errorMap;
    }

    private String processJsonSafely(JsonSurfer surfer, String json, String path) {
        try {
            Object result = surfer.collectOne(json, path);
            if (result != null) {
                log.info("Found value: " + result);
            } else {
                return "No value found for path: " + path;
            }
        } catch (JsonSurfingException e) {
            log.error("JSON processing error: " + e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            log.error("Unexpected error: " + e.getMessage());
            return e.getMessage();
        }
        return json;
    }
}
// COMMON JSONPATH EXPRESSIONS
//$.field - Root level field
//$.field.subfield - Nested field access
//$.array[0] - First element of array
//$.array[*] - All array elements
//$.array[-1] - Last array element
//$..field - Recursive descent (find field anywhere)
//$.array[?(@.field > 5)] - Filter array elements
//$.array[0:3] - Array slice (elements 0,1,2)