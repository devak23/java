package com.ak.rnd.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/docker/v1")
public class HelloResource {

    @GetMapping("/greet")
    public String greet() {
        return "Hello From SpringBoot!";
    }
}
