package com.ak.rnd.dockertest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.ak.rnd.resource")
@SpringBootApplication
public class DockerTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerTestApplication.class, args);
	}

}
