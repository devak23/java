package com.rnd.learning.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rnd.learning.app.fixtures.EmployeeFixture;
import com.rnd.learning.app.model.Employee;
import com.rnd.learning.app.service.IEmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerUTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployee_whenCreateEmployeeIsInvoked_thenReturnSavedEmployee() throws Exception {
        // given
        Employee emp = EmployeeFixture.getSampleEmployee();
        BDDMockito.given(employeeService.saveEmployee(any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emp)));

        // then
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(emp.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(emp.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(emp.getEmail())));

    }

    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnListOfEmployees() throws Exception {
        // given
        BDDMockito.given(employeeService.getAllEmployees()).willReturn(EmployeeFixture.getSampleListOfEmployees());

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee"));

        // then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(2)));
    }
}
