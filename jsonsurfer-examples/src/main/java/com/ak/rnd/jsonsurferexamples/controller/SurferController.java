package com.ak.rnd.jsonsurferexamples.controller;

import com.ak.rnd.jsonsurferexamples.service.Generator;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.jsfr.json.JsonSurfer;
import org.jsfr.json.JsonSurferJackson;
import org.jsfr.json.SurfingConfiguration;
import org.jsfr.json.exception.JsonSurfingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RestController
@RequestMapping("/api/v1/surfer")
public class SurferController {

    @Autowired
    @Qualifier("csvGenerator")
    private Generator generator;
    
    @GetMapping("/")
    public String hello() throws IOException {
        ClassPathResource resource = new ClassPathResource("index.html");
        return Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
    }

    @GetMapping("/basic")
    public String basic() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/basic.json");
        String json = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);

        JsonSurfer surfer = JsonSurferJackson.INSTANCE;
        
        // Extract the name and age fields using modern API
        AtomicReference<Object> name = new AtomicReference<>();
        AtomicReference<Object> age = new AtomicReference<>();
        
        SurfingConfiguration config = SurfingConfiguration.builder()
                .bind("$.name", (value, context) -> name.set(value))
                .bind("$.age", (value, context) -> age.set(value))
                .build();
        
        surfer.surf(json, config);
        
        log.info("Name: " + name.get());
        log.info("Age: " + age.get());
        
        return "Name: " + name.get() + ", Age: " + age.get();

        // Key concepts:
        //JsonSurferJackson.INSTANCE - Using Gson as the JSON provider
        //collectOne() - Extracts a single value matching the JsonPath
        //$.name - JsonPath expression to select the "name" field
    }
    
    @GetMapping("/basic-arrays")
    public Map<String, Object> basicArrays() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/basic-arrays.json");
        String json = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);

        JsonSurfer surfer = JsonSurferJackson.INSTANCE;
        
        // Extract all names from 'users' array using modern API
        List<Object> names = new ArrayList<>();
        AtomicReference<Object> firstName = new AtomicReference<>();
        List<Object> olderUsers = new ArrayList<>();
        
        SurfingConfiguration config = SurfingConfiguration.builder()
                .bind("$.users[*].name", (value, context) -> names.add(value))
                .bind("$.users[0].name", (value, context) -> firstName.set(value))
                .bind("$.users[?(@.age > 28)].name", (value, context) -> olderUsers.add(value))
                .build();
        
        surfer.surf(json, config);
        
        log.info("Names: " + names);
        log.info("First Name: " + firstName.get());
        log.info("Older Users: " + olderUsers);
        
        return Map.of("names", names, "firstName", firstName.get(), "olderUsers", olderUsers);


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
     
        JsonSurfer surfer = JsonSurferJackson.INSTANCE;

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
        JsonSurfer surfer = JsonSurferJackson.INSTANCE;

        // Using modern API to extract data
        AtomicReference<Object> companyName = new AtomicReference<>();
        List<Object> departments = new ArrayList<>();
        List<Object> employees = new ArrayList<>();
        List<Object> skills = new ArrayList<>();
        List<Object> engineeringEmployees = new ArrayList<>();

        SurfingConfiguration config = SurfingConfiguration.builder()
                .bind("$.company.name", (value, context) -> companyName.set(value))
                .bind("$.company.departments[*].name", (value, context) -> departments.add(value))
                .bind("$.company.departments[*].employees[*].name", (value, context) -> employees.add(value))
                .bind("$.company.departments[*].employees[*].skills[*]", (value, context) -> skills.add(value))
                .bind("$.company.departments[?(@.name == 'Engineering')].employees[*].name", (value, context) -> engineeringEmployees.add(value))
                .build();

        surfer.surf(json, config);

        result.put("companyName", companyName.get());
        result.put("departments", departments);
        result.put("employees", employees);
        result.put("skills", skills);
        result.put("engineeringEmployees", engineeringEmployees);

        return result;
    }
    
    
    @GetMapping("/state-management")
    public Map<String, Object> stateManagement() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/state-management.json");
        String json = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
        JsonSurfer surfer = JsonSurferJackson.INSTANCE;

        // State management for aggregating data
        Map<String, Double> regionTotals = new HashMap<>();
        AtomicInteger recordCount = new AtomicInteger(0);

        SurfingConfiguration config = SurfingConfiguration.builder()
                .bind("$.sales[*]", (value, context) -> {
                    recordCount.incrementAndGet();

                    // Extract region and amount from the current sale record using modern API
                    AtomicReference<Object> regionObject = new AtomicReference<>();
                    AtomicReference<Object> amountObject = new AtomicReference<>();
                    
                    SurfingConfiguration innerConfig = SurfingConfiguration.builder()
                            .bind("$.region", (val, ctx) -> regionObject.set(val))
                            .bind("$.amount", (val, ctx) -> amountObject.set(val))
                            .build();
                    
                    surfer.surf(value.toString(), innerConfig);

                    if (regionObject.get() != null && amountObject.get() != null) {
                        String region = regionObject.get().toString();
                        double amount = Double.parseDouble(amountObject.get().toString());
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

        JsonSurfer surfer = JsonSurferJackson.INSTANCE;
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
            AtomicReference<Object> result = new AtomicReference<>();
            
            SurfingConfiguration config = SurfingConfiguration.builder()
                    .bind(path, (value, context) -> result.set(value))
                    .build();
            
            surfer.surf(json, config);
            
            if (result.get() != null) {
                log.info("Found value: " + result.get());
                return "Found value: " + result.get();
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
    }
    
    @GetMapping("/basic-grid-data")
    public Map<String, Object> basicGridData() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/grid-definition.json");
        String json =  Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);

        Map<String, Object> result = new HashMap<>();
        JsonSurfer surfer = JsonSurferJackson.INSTANCE;
        
        // Extract all information using modern API
        AtomicReference<Object> headerInfo = new AtomicReference<>();
        AtomicReference<Object> configInfo = new AtomicReference<>();
        AtomicReference<Object> entityInfo = new AtomicReference<>();
        AtomicReference<Object> gridInfo = new AtomicReference<>();
        AtomicReference<Object> footerInfo = new AtomicReference<>();
        
        SurfingConfiguration config = SurfingConfiguration.builder()
                .bind("$.headerInfo", (value, context) -> headerInfo.set(value))
                .bind("$.configInfo", (value, context) -> configInfo.set(value))
                .bind("$.entityInfo", (value, context) -> entityInfo.set(value))
                .bind("$.gridInfo", (value, context) -> gridInfo.set(value))
                .bind("$.footerInfo", (value, context) -> footerInfo.set(value))
                .build();
        
        surfer.surf(json, config);
        
        result.put("headerInfo", headerInfo.get());
        result.put("configInfo", configInfo.get());
        result.put("entityInfo", entityInfo.get());
        result.put("gridInfo", gridInfo.get());
        result.put("footerInfo", footerInfo.get());

        return result;
    }
    
    @GetMapping("/csv-grid-data")
    public ResponseEntity<StreamingResponseBody> csvGridData() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/grid-definition-with-data.json");
        
        // Set up HTTP headers for CSV download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv; charset=UTF-8"));
        headers.setContentDispositionFormData("attachment", "grid-data.csv");
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setPragma("no-cache");
        headers.setExpires(0);
        
        StreamingResponseBody stream = outputStream -> {
            try (java.io.InputStream inputStream = resource.getInputStream()) {
                // Use single-pass processing for memory efficiency in Kubernetes environment
                generator.generate(inputStream, outputStream);
            } catch (Exception e) {
                log.error("Error during CSV streaming: " + e.getMessage(), e);
                throw new RuntimeException("CSV generation failed", e);
            }
        };
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(stream);
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