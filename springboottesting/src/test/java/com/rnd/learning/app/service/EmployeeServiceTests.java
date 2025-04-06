package com.rnd.learning.app.service;

import com.rnd.learning.app.exception.InvalidResourceException;
import com.rnd.learning.app.fixtures.EmployeeFixture;
import com.rnd.learning.app.model.Employee;
import com.rnd.learning.app.repository.IEmployeeRepository;
import com.rnd.learning.app.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private IEmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @DisplayName("Save employee saveMethod when employee doesn't exist")
    @Test
    public void givenEmployee_whenSaveEmployee_thenReturnSavedEmployee() {
        // given
        Employee employee = EmployeeFixture.getSampleEmployee();

        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        // when
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then
        Assertions.assertThat(savedEmployee).isNotNull();
        Mockito.verify(employeeRepository, Mockito.times(1)).findByEmail(employee.getEmail());
        Mockito.verify(employeeRepository, Mockito.times(1)).save(employee);
        Mockito.verifyNoMoreInteractions(employeeRepository);
    }

    @DisplayName("Save employee saveMethod when employee already exists")
    @Test
    public void givenExistingEmployeeEmail_whenSaveEmployee_thenThrowInvalidResourceException() {
        // given
        Employee employee = EmployeeFixture.getSampleEmployee();
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        // when
        Assertions.assertThatThrownBy(() -> employeeService.saveEmployee(employee))
                .isInstanceOf(InvalidResourceException.class)
                .hasMessage("Employee with email " + employee.getEmail() + " already exists!");

        // then
        Mockito.verify(employeeRepository, Mockito.times(1)).findByEmail(employee.getEmail());
        Mockito.verify(employeeRepository, Mockito.never()).save(Mockito.any(Employee.class));
        Mockito.verifyNoMoreInteractions(employeeRepository);
    }
}
