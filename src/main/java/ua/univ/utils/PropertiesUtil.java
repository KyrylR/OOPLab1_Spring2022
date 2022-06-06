package ua.univ.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static final String PROPERTY_FILE_PATH = "app.properties";

    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        try (InputStream inputStream =
                     Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTY_FILE_PATH)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties.\n", e);
        }
    }

    private PropertiesUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getProperty(String propertyName) {
        return PROPERTIES.getProperty(propertyName);
    }
}

