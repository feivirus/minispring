package com.minispring.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyUtil {
    
    public static Properties loadProperties(String fileName) {
        InputStream is = null;
        Properties properties = null;
        
        try {
            is = ClassUtil.getClassLoader().getResourceAsStream(fileName);
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
    
    public static String getString(Properties properties, String key) {
        return getString(properties, key, "");
    }
    
    public static String getString(Properties properties, String key, String defaultValue) {
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        } else {
            return defaultValue;
        }
    }
}
