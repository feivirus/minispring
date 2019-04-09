package com.minispring.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.minispring.framework.annotation.Inject;
import com.minispring.framework.util.ReflectionUtil;

/**
 * 注入带有inject注解的字段
 * @author feivirus
 *
 */
public class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        
        if (MapUtils.isNotEmpty(beanMap)) {
            for(Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();
                Field[] fieldList = beanClass.getDeclaredFields();
                
                if (ArrayUtils.isNotEmpty(fieldList)) {
                    for(Field field : fieldList) {
                        if (field.isAnnotationPresent(Inject.class)) {
                            Class<?> beanFieldType = field.getType();
                            Object fieldInstance = beanMap.get(beanFieldType);
                            
                            ReflectionUtil.setField(field, beanInstance, fieldInstance);
                            
                        }
                    }
                }
            }
        }
    }
}
