package com.pearson.registrationassistant.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A helper class to read properties file on the classpath.
 * Created by abhaykulkarni on 08/12/16.
 */
public final class PropertiesHelper {
    private static final PropertiesHelper instance = new PropertiesHelper();
    private Properties properties;

    /**
     * Singleton
     */
    private PropertiesHelper() {
        properties = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Could not read 'application.properties'",e);
        }
    }

    public static final PropertiesHelper getInstance() {
        return instance;
    }

    public String getProperty(String key) {
        return (String) properties.get(key);
    }
}
