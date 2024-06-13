package com.rnd.sage.framework.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Objects;
import java.util.Properties;

public class CustomYamlParserFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
        Properties properties = loadYaml(resource);

        return new PropertiesPropertySource(name != null ? name
                : Objects.requireNonNull(resource.getResource().getFilename(), "Error loading Properties"), properties);
    }

    private Properties loadYaml(EncodedResource resource) {
        try {
            YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
            factoryBean.setResources(resource.getResource());
            factoryBean.afterPropertiesSet();
            return factoryBean.getObject();
        } catch (Exception ex) {
            throw new RuntimeException("Invalid Yaml config");
        }
    }
}
