package com.minispring.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

public class ReflectionUtil {
    
    public static Object newInstance(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        Object obj = null;
        
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }
    
    public static Object newInstance(String className) {
        if (StringUtils.isBlank(className)) {
            return null;
        }
        
        Class<?> clazz = ClassUtil.loadClass(className);
        
        return newInstance(clazz);
    }
    
    public static Object invokeMethod(Object obj, Method method, Object... param) {
        Object ret  = null;
        
        try {
            ret = method.invoke(obj, param);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    public static void setField(Field field, Object obj, Object value) {
        field.setAccessible(true);
        
        try {
            field.set(obj, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }        
    }
}
