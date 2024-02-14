package com.ak.rnd.excel.excelcsv.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Map;



@Getter
@Configuration
public class AppConfig {

    @Value("#{${method.header.employee}}")
    private Map<String, String> employeeModelMap;

    @Value("#{${method.header.business}}")
    private Map<String, String> businessModelMap;

    @Value("#{${method.header.commerce}}")
    private Map<String, String> commerceModelMap;

    public Map<String, String> getModelMap(String key) {
        if (key.equalsIgnoreCase("Business")) {
            return businessModelMap;
        }

        if (key.equalsIgnoreCase("Employee")) {
            return employeeModelMap;
        }

        if (key.equalsIgnoreCase("Commerce")) {
            return commerceModelMap;
        }

        return Collections.emptyMap();
    }
}
