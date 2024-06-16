package com.rnd.learning.app.repository;

import com.rnd.learning.app.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
