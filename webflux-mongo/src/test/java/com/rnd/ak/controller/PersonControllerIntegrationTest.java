package com.rnd.ak.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PersonControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    private String personMSUrl = "http://localhost:8080/playground/person/stream/123";

    /**
     * Test the status endpoint.
     */
    @Test
    void testStatus() throws Exception {
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(content().string("I'm here"));
    }

    /**
     * Test the stream endpoint.
     * Mock the external streaming response.
     */
    @Test
    void testStreamPeople() throws Exception {
        // Set up MockRestServiceServer for mocking the RestTemplate call
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        String sampleJsonResponse = "[{\"id\":1,\"name\":\"John\"},{\"id\":2,\"name\":\"Jane\"}]";


        // Mock the external service behavior
        mockServer.expect(requestTo(personMSUrl))
                .andExpect(method(org.springframework.http.HttpMethod.GET))
                .andRespond(withSuccess(sampleJsonResponse.getBytes(), MediaType.APPLICATION_OCTET_STREAM));

        // Perform request to the controller and validate streamed response
        mockMvc.perform(get("/person/stream/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("John")))
                .andExpect(content().string(containsString("Jane")));

        // Ensure the mock server interactions are verified
        mockServer.verify();
    }
}