package com.rnd.sage;

import com.rnd.sage.framework.config.ProcessorConfig;
import com.rnd.sage.framework.service.IProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class YetAnotherFrameworkApplication implements CommandLineRunner {
	@Autowired
	private ApplicationContext context;
	@Autowired
	private ProcessorConfig config;

	public static void main(String[] args) {
		SpringApplication.run(YetAnotherFrameworkApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		config.getProcessors().forEach(p -> {
			IProcess process = (IProcess) context.getBean(p.getQualifier());
			process.execute();
		});
	}
}
