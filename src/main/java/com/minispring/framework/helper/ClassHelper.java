package com.minispring.framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.minispring.framework.annotation.Controller;
import com.minispring.framework.annotation.Service;
import com.minispring.framework.util.ClassUtil;

public final class ClassHelper {
    public static final Set<Class<?>> CLASS_SET;
    
    static {
        String appPackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(appPackage);
    }
    
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClazz) {
        Set<Class<?>> childSet = new HashSet<>();
        
        for(Class<?> clazz : CLASS_SET) {
            if (superClazz.isAssignableFrom(clazz)) {
                childSet.add(clazz);
            }
        }
        return childSet;
    }
    
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<>();
        
        for(Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }
    
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> serviceSet = new HashSet<Class<?>>();
        
       for(Class<?> clazz : CLASS_SET) {
           if (clazz.isAnnotationPresent(Service.class)) {
               serviceSet.add(clazz);
           }
       }
       
       return serviceSet;
    }
    
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> controllerSet = new HashSet<Class<?>>();
        
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                controllerSet.add(clazz);
            }
        }
        return controllerSet;
    }
    
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanSet = new HashSet<Class<?>>();
        
        beanSet.addAll(getControllerClassSet());
        beanSet.addAll(getServiceClassSet());
        return beanSet;        
    }
}
