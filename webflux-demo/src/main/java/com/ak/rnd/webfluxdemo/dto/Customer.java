package com.ak.rnd.webfluxdemo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private long id;
    private String name;
}
