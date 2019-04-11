package com.minispring.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.minispring.framework.util.ReflectionUtil;

public class BeanHelper {
    public static Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();
    
    static {
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        
        for (Class<?> clazz : classSet) {
            Object obj = ReflectionUtil.newInstance(clazz);
            
            BEAN_MAP.put(clazz, obj);            
        }       
    }
    
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }
    
    public static <T> T getBean(Class<T> clazz) {
        if (!BEAN_MAP.containsKey(clazz)) {
            return null;
        }
        Object bean = BEAN_MAP.get(clazz);
        if (bean == null) {
            return (T)ReflectionUtil.newInstance(clazz);
        } else {
            return (T)bean;
        }       
    }
    
    public static void setBean(Class<?> clazz, Object bean) {
        BEAN_MAP.put(clazz, bean);
    }
    
}
