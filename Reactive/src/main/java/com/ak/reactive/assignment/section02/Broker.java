package com.ak.reactive.assignment.section02;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Builder
@Getter
@ToString
public class Broker {
    private String name;
    private boolean subscribed;
}
