package com.ak.rnd.framework;

import com.ak.rnd.framework.model.AdviceConfig;
import com.ak.rnd.framework.model.BaseConfig;
import com.ak.rnd.framework.model.BaseModule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;

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
				if (module.getType().equalsIgnoreCase("file")) {
					try (InputStream modStream = TypeReference.class.getResourceAsStream("/" + module.getSource())) {
						AdviceConfig adviceConfig = mapper.readValue(modStream, AdviceConfig.class);
						System.out.println(adviceConfig);
					}
				}
			}
		}
	}
}
