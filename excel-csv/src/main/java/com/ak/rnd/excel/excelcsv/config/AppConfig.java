package com.ak.rnd.excel.excelcsv.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;



@Getter
@Configuration
public class AppConfig {
    @Value("#{${headers}}")
    private Map<String, String> modelMap;
}
