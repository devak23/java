package com.rnd.ak.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
@Slf4j
public class PersonController {
    @Value("${person.ms.url}")
    private String personMSUrl;


    private final RestTemplate restTemplate;

    @GetMapping
    public String status() {
        return "I'm here";
    }

    @GetMapping(value = "/stream/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void streamPeople(@PathVariable("uid") String uid, HttpServletResponse response) {
        log.info("Received a streaming request for uid: {}", uid);
        try {
            // fetch the streaming response from external service
            restTemplate.execute(personMSUrl
                    , HttpMethod.GET
                    , null /* request call back is not required*/
                    , clientHttpResponse -> {
                        // Set the content type to JSON
                        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

                        // Get the response body
                        InputStream inputStream = clientHttpResponse.getBody();
                        try (OutputStream outputStream = response.getOutputStream()) {
                            byte[] buffer = new byte[8192];
                            int bytesRead;

                            // Copy the stream directly to the HTTP response
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                                outputStream.flush();
                            }
                        }
                        return null; // Return type must be Void or null
                    }
                    ,uid);




        } catch (Exception e) {
            log.error("Error while receiving a streaming response", e);
            throw new RuntimeException("Failed to receive data for uid: " + uid, e);
        }

    }
}
