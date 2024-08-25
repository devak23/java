package com.ak.rnd.webfluxdemo.service;

import com.ak.rnd.webfluxdemo.dao.CustomerDAO;
import com.ak.rnd.webfluxdemo.dto.Customer;
import com.ak.rnd.webfluxdemo.util.TimeIt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerDAO customerDAO;

    @TimeIt
    public List<Customer> getAllCustomers() {
        return customerDAO.getCustomersImperative();
    }

    @TimeIt
    public Flux<Customer> getAllCustomersStream() {
        return customerDAO.getCustomersReactive();
    }
}
