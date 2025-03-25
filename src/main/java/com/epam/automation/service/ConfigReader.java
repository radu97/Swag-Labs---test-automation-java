package com.epam.automation.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    public static final String FAILED_TO_LOAD_CONFIG = "Failed to load configuration file";
    public static final String CONFIG_PATH = "resources/config.properties";     //path to the configuration file

    // a static Properties object that will store key-value pairs from the config file
    private static final Properties properties = new Properties();

    static {
        // creates a FileInputStream object to read the file located at CONFIG_PATH
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_PATH)) {
            // loads the properties from the config file into the Properties object
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(FAILED_TO_LOAD_CONFIG, e);
        }
    }

    // this method retrieves a property value based on the provided key
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

