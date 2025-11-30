package com.ak.rnd.jsonsurferexamples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsynConfig {
    @Bean("pdfGeneratorThread")
    public Executor pdfGeneratorExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);        // 5 threads always running
        executor.setMaxPoolSize(10);        // Max 10 threads under load
        executor.setQueueCapacity(100);     // Queue up to 100 tasks
        executor.setThreadNamePrefix("pdf-gen-");
        executor.initialize();
        return executor;
    }
}
