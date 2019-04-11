package com.minispring.framework.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

public class AspectProxy implements Proxy{

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> targetClass = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        
        if (intercept(targetClass, method, params)) {
            before(targetClass, method, params);
            result = proxyChain.doProxyChain();
            after(targetClass, method, params);
        } else {
            result = proxyChain.doProxyChain();
        }
        end(targetClass, method, params);
        
        return result;
    }
    
    public void begin() {
        
    }
    
    public void before(Class<?> clazz, Method method, Object[] params) {
        
    }
    
    public void after(Class<?> clazz, Method method, Object[] params) {
        
    }
    
    public void end(Class<?> clazz, Method method, Object[] params) {
        
    }
    
    public boolean intercept(Class<?> clazz, Method method, Object[] params) {
        return true;
    }
    
}
