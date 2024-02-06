package com.ak.reactive.lectures.section4.helper;

import com.ak.reactive.utils.Util;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Customer {
    private int customerId;
    private String name;

    public Customer(int customerId) {
        this.customerId = customerId;
        this.name = Util.faker().name().fullName();
    }
}
