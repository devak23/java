package com.ak.rnd.excel.excelcsv.dao;

import com.ak.rnd.excel.excelcsv.model.Business;
import com.ak.rnd.excel.excelcsv.model.Employee;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class DataProvider {
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

    public Flux<Business> getBusinesses(int count) {
        com.github.javafaker.Business db = faker.business();
        return Flux.range(1, count).map(i -> Business
                .builder()
                .creditCardType(db.creditCardType())
                .creditCardExpiry(db.creditCardExpiry())
                .creditCardNumber(db.creditCardNumber())
                .build()
        );
    }

    public Flux<?> getData(String key, int count) {
        if (key.equalsIgnoreCase("Employee")) {
            return getEmployees(count);
        }

        if (key.equalsIgnoreCase("Business")) {
            return getBusinesses(count);
        }

        return Flux.empty();
    }
}
