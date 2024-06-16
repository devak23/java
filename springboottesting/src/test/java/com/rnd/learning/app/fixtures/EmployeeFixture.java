package com.rnd.learning.app.fixtures;

import com.rnd.learning.app.model.Employee;

import java.util.Arrays;
import java.util.List;

public final class EmployeeFixture {

    public static Employee getSampleEmployee() {

        return Employee.builder()
                .firstName("Abhay")
                .lastName("Kulkarni")
                .email("abhayk@gmail.com")
                .build();
    }

    public static List<Employee> getSampleListOfEmployees() {
        Employee emp1 = Employee.builder().firstName("Soham").lastName("Kulkarni").build();
        Employee emp2 = Employee.builder().firstName("Abhay").lastName("Kulkarni").build();

        return Arrays.asList(emp1, emp2);
    }
}
