package com.minispring.framework.bean;

import java.lang.reflect.Method;

import lombok.Data;

@Data
public class Handler {
    private Class<?> clazz;
    
    private Method method;
    
    public Handler(Class<?> clazz, Method method) {
        this.clazz = clazz;
        
        this.method = method;
    }
}
