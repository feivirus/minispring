package com.minispring.framework.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import net.sf.cglib.proxy.MethodProxy;

@Data
public class ProxyChain {
    private Class<?> targetClass;
    
    private Object targetObject;
    
    private List<Proxy> proxyList = new ArrayList<Proxy>();
    
    private Integer proxyIndex = 0;
    
    private MethodProxy methodProxy;
    
    private Object[] methodParams;
    
    private Method targetMethod;
    
    public ProxyChain(Class<?> targetClass, Object targetObject, List<Proxy> proxyList, 
            MethodProxy methodProxy, Object[] methodParams, Method targetMethod) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.proxyList = proxyList;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.targetMethod = targetMethod;
    }
    
    public Object doProxyChain() throws Throwable{
        Object object = null;
        try {
            if (proxyIndex < proxyList.size()) {
                Proxy proxy = proxyList.get(proxyIndex);
                proxyIndex++;
                object = proxy.doProxy(this);
            } else {
               object = methodProxy.invokeSuper(targetObject, methodParams);               
            }
        } catch (Exception e) {            
        }
        return object;
    }
}
