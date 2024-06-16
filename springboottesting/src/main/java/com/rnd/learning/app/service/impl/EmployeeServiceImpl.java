package com.rnd.learning.app.service.impl;

import com.rnd.learning.app.exception.InvalidResourceException;
import com.rnd.learning.app.model.Employee;
import com.rnd.learning.app.repository.IEmployeeRepository;
import com.rnd.learning.app.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {
    private final IEmployeeRepository repository;

    @Override
    public Employee saveEmployee(Employee employee) throws InvalidResourceException {
        Optional<Employee> empOptional = repository.findByEmail(employee.getEmail());
        if (empOptional.isPresent()) {
            throw new InvalidResourceException("Employee already exists!");
        }

        return repository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return repository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public void deleteEmployee(long id) {
        repository.deleteById(id);
    }
}
