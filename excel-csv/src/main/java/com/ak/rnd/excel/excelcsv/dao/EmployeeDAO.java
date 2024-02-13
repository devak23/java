package com.ak.rnd.excel.excelcsv.dao;

import com.ak.rnd.excel.excelcsv.model.Employee;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class EmployeeDAO {
    private final Faker faker = Faker.instance();

    public Flux<Employee> getEmployees(int count) {
        Name db = faker.name();
        return Flux.range(1, count).map(i -> Employee
                .builder()
                .title(db.title())
                .firstName(db.firstName())
                .fullName(db.fullName())
                .lastName(db.lastName())
                .bloodGroup(db.bloodGroup())
                .userName(db.username())
                .build());
    }
}
