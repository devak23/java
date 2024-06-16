package com.rnd.learning.app.repository;

import com.rnd.learning.app.fixtures.EmployeeFixture;
import com.rnd.learning.app.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    // Junit test for saveEmployee operation

    @DisplayName("Testing Save")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {
        // given
        Employee employee = EmployeeFixture.getSampleEmployee();

        // when
        Employee savedEmployee = employeeRepository.save(employee);

        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isNotNull();
        assertThat(savedEmployee.getFirstName()).isEqualTo("Abhay");
        assertThat(savedEmployee.getLastName()).isEqualTo("Kulkarni");
    }
    
    @DisplayName("Testing findlAll")
    @Test
    public void givenMultipleEmployeeObjectsSaved_whenInvokeFindAll_thenReturnMultipleEmployees() {
        // given - define precondition for test
        employeeRepository.saveAll(EmployeeFixture.getSampleListOfEmployees());

        // when - perform the desiredAction
        List<Employee> employees = employeeRepository.findAll();

        // then - verify the output
        assertThat(employees).isNotNull();
        assertThat(employees).isNotEmpty();
        assertThat(employees.size()).isEqualTo(2);
    }

    @DisplayName("Testing findById")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() {
        // given - define precondition for test
        Employee emp = EmployeeFixture.getSampleEmployee();
        Long empId = employeeRepository.save(emp).getId();

        // when - perform the desiredAction
        Optional<Employee> empOptional =  employeeRepository.findById(empId);

        // then - verify the output
        assertThat(empOptional.isPresent()).isEqualTo(true);
        assertThat(empOptional.get()).isEqualTo(emp);
    }
    
    @DisplayName("Testing findByEmail")
    @Test
    public void givenEmailAddress_whenFindByEmail_thenReturnRelatedEmployee() {
        // given - define precondition for test
        Employee emp = EmployeeFixture.getSampleEmployee();
        employeeRepository.save(emp);
        
        // when - perform the desiredAction
        Optional<Employee> empOptional = employeeRepository.findByEmail("abhayk@gmail.com");
    
        // then - verify the output
        assertThat(empOptional.isPresent()).isEqualTo(true);
        Employee actual = empOptional.get();
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(emp);
    }
}
