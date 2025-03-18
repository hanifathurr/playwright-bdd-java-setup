package com.basesetup.playwright.utils.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {
    private static final Logger logger = LoggerFactory.getLogger(PropertyFileReader.class);
    private static final Properties properties = new Properties();
    private static final String DEFAULT_ENV = "dev"; // Fallback environment
    private static boolean isLoaded = false;

    static {
        loadProperties();
    }

    private static synchronized void loadProperties() {
        if (isLoaded) return; // Prevent reloading

        String env = System.getProperty("env", System.getenv("ENV"));
        if (env == null || env.isEmpty()) {
            env = DEFAULT_ENV;
        }

        String configPath = "src/main/java/com/basesetup/playwright/utils/config/config-" + env + ".properties";

        try (FileInputStream fis = new FileInputStream(configPath)) {
            properties.load(fis);
            isLoaded = true;
            logger.info("✅ Loaded environment configuration: {}", configPath);
        } catch (IOException e) {
            logger.error("❌ Failed to load config file: {} | Error: {}", configPath, e.getMessage());
            throw new RuntimeException("Error reading config file: " + configPath, e);
        }
    }

    /**
     * Retrieves a string property from the configuration file.
     *
     * @param key          The property key.
     * @param defaultValue Default value if the key is not found.
     * @return The property value or the default value.
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Retrieves a string property from the configuration file.
     *
     * @param key The property key.
     * @return The property value or null if not found.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Retrieves a boolean property from the configuration file.
     *
     * @param key          The property key.
     * @param defaultValue Default boolean value if the key is not found.
     * @return The boolean property value.
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        return Boolean.parseBoolean(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    /**
     * Retrieves an integer property from the configuration file.
     *
     * @param key          The property key.
     * @param defaultValue Default integer value if the key is not found or invalid.
     * @return The integer property value or the default value.
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                logger.warn("⚠️ Invalid integer format for key '{}': '{}'. Using default: {}", key, value, defaultValue);
            }
        }
        return defaultValue;
    }
}
