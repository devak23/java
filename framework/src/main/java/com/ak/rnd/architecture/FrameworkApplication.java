package com.ak.rnd.architecture;

import com.ak.rnd.architecture.model.BaseConfig;
import com.ak.rnd.architecture.model.BaseModule;
import com.ak.rnd.architecture.processor.AdviceProcessor;
import com.ak.rnd.architecture.processor.IProcess;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.lang.reflect.Method;

@SpringBootApplication
public class FrameworkApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FrameworkApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try (InputStream stream = TypeReference.class.getResourceAsStream("/application.yml")) {
			BaseConfig baseConfig = mapper.readValue(stream, BaseConfig.class);
			System.out.println(baseConfig);

			for(BaseModule module : baseConfig.getModules()) {
				Class<?> clazz = Class.forName(String.valueOf(module.getProcessor()));
				IProcess<String> processor = (AdviceProcessor) clazz.newInstance();
				Method process = clazz.getDeclaredMethod("process");
				process.invoke(processor);
			}
		}
	}
}
