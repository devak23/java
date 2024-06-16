package com.rnd.learning.app.repository;

import com.rnd.learning.app.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    // Junit test for saveEmployee operation

    @DisplayName("Save Test")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {
        // given
        Employee employee = Employee.builder()
                .firstName("Abhay")
                .lastName("Kulkarni")
                .email("abhayk@gmail.com")
                .build();

        // when
        Employee savedEmployee = employeeRepository.save(employee);

        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isNotNull();
        assertThat(savedEmployee.getFirstName()).isEqualTo("Abhay");
        assertThat(savedEmployee.getLastName()).isEqualTo("Kulkarni");
    }
    
    @DisplayName("findlAll Test")
    @Test
    public void givenMultipleEmployeeObjectsSaved_whenInvokeFindAll_thenReturnMultipleEmployees() {
        // given - define precondition for test
        Employee emp1 = Employee.builder().firstName("Soham").lastName("Kulkarni").build();
        Employee emp2 = Employee.builder().firstName("Abhay").lastName("Kulkarni").build();

        employeeRepository.saveAll(Arrays.asList(emp1, emp2));

        // when - perform the desiredAction
        List<Employee> employees = employeeRepository.findAll();

        // then - verify the output
        assertThat(employees).isNotNull();
        assertThat(employees).isNotEmpty();
        assertThat(employees.size()).isEqualTo(2);
    }
}
