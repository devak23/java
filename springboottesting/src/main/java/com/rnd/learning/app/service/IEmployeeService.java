package com.rnd.learning.app.service;

import com.rnd.learning.app.exception.InvalidResourceException;
import com.rnd.learning.app.model.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    Employee saveEmployee(Employee employee) throws InvalidResourceException;

    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(long id);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(long id);
}
