package com.rnd.learning.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rnd.learning.app.fixtures.EmployeeFixture;
import com.rnd.learning.app.model.Employee;
import com.rnd.learning.app.repository.IEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IEmployeeRepository repository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @DisplayName("createEmployeeIT")
    @Test
    public void givenEmployee_whenCreateEmployeeIsInvoked_thenReturnSavedEmployee() throws Exception {
        // given - define precondition for test
        Employee emp = EmployeeFixture.getSampleEmployee();

        // when - perform the desiredAction
        ResultActions response = mockMvc.perform(post("/api/v1/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emp))
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(emp.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(emp.getLastName())))
                .andExpect(jsonPath("$.email", is(emp.getEmail())))
        ;
    }
}
