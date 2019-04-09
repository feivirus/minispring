package com.minispring.framework.helper;

import java.util.Properties;

import com.minispring.framework.config.ConfigConstant;
import com.minispring.framework.util.PropertyUtil;

public final class ConfigHelper {
    private static final Properties CONFIG_PROP = PropertyUtil.loadProperties(ConfigConstant.CONFIG_FILE);
    
    public static String getJDBCDriver() {
        return PropertyUtil.getString(CONFIG_PROP, ConfigConstant.JDBC_DRIVER);
    }
    
    public static String getJDBCURL() {
        return PropertyUtil.getString(CONFIG_PROP, ConfigConstant.JDBC_URL);
    }
    
    public static String getJDBCUserName() {
        return PropertyUtil.getString(CONFIG_PROP, ConfigConstant.JDBC_USERNAME);
    }
    
    public static String getJDBCPassword() {
        return PropertyUtil.getString(CONFIG_PROP, ConfigConstant.JDBC_PASSWORD);
    }
    
    public static String getAppBasePackage() {
        return PropertyUtil.getString(CONFIG_PROP, ConfigConstant.APP_BASE_PACKAGE);
    }
    
    public static String getAppJspPath() {
        return PropertyUtil.getString(CONFIG_PROP, ConfigConstant.APP_JSP_PATH);
    }
    
    public static String getAppAssertPath() {
        return PropertyUtil.getString(CONFIG_PROP, ConfigConstant.APP_ASSERT_PATH);
    }
}
