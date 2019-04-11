package com.minispring.framework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.minispring.framework.annotation.Aspect;
import com.minispring.framework.proxy.AspectProxy;
import com.minispring.framework.proxy.Proxy;
import com.minispring.framework.proxy.ProxyChain;
import com.minispring.framework.proxy.ProxyManager;

public class AopHelper {
    
    static {
        try {
            //以UserController,Proxy为正向,一对多的关系
            //获取拦截类，拦截目标的map,类似ControllerAspect, Set<Class>{UserController}, 获取逆向关系
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            //创建目标类到代理类的映射, 形如UserController,ControllerAspect,遍历逆向关系，获取正向关系
            Map<Class<?>, List<Proxy>> targetMap = createTargetClassMap(proxyMap);
            
            for(Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()) {
                Class<?> targetClass = entry.getKey();
                List<Proxy> proxyList = entry.getValue();
                
                //把UserController的代理类连接成代理链表
                ProxyChain chain = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, chain);
            }
            
        } catch (Exception e) {
        }
    }
    
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> classSet = new HashSet<>();
        Class<? extends Annotation> targetAnnotation = aspect.value();
        
        if (targetAnnotation != null && 
             (!targetAnnotation.equals(Aspect.class))) {
            classSet.addAll(ClassHelper.getClassSetByAnnotation(targetAnnotation));
        }
        return classSet;
    }
    
    /**
     * 1.遍历得到代理类
     * 2.根据代理类aspect得到所有目标类，比如Controller的，Service的
     * 返回类似ControllerAspect, Set<Class>{UserController}的形似,即拦截类，拦截目标的map
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception{
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        //获取所有AspectProxy类的子类，即拦截类,比如ControllerAspect
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        
        for(Class<?> clazz : proxyClassSet) {
            //拦截类必须带有Aspect注解，才能确定拦截目标，才能使用
            if (clazz.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = clazz.getAnnotation(Aspect.class);
                //获取拦截指定注解的类，比如带有Controller注解的目标类,UserController是一个
                Set<Class<?>> targetSet = createTargetClassSet(aspect);
                
                proxyMap.put(clazz, targetSet);
            }
        }
        return proxyMap;
    }
    
    /**
     * 遍历所有代理类，反向，建立目标类到代理类实例的映射
     * 返回形如UserController,ControllerAspect代理类实例
     * @param proxyMap 形如 ControllerAspect, Set<Class>{UserController}
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetClassMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception{
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        
        for(Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {
            Class<?> proxyClass = entry.getKey();
            Set<Class<?>> targetClassSet = entry.getValue();
            
            //每个目标类的代理类可能有多个
           for(Class<?> targetClass : targetClassSet) {
               //比如ControllerAspect的实例
               Proxy proxyInstance = (Proxy)proxyClass.newInstance();
               
               if (targetMap.containsKey(targetClass)) {
                   targetMap.get(targetClass).add(proxyInstance);
               } else {
                   List<Proxy> proxyInstanceList = new ArrayList<Proxy>();
                   
                   proxyInstanceList.add(proxyInstance);
                   targetMap.put(targetClass, proxyInstanceList);
               }
           }
        }
        
        return targetMap;
    }
}
