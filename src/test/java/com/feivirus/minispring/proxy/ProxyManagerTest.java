package com.feivirus.minispring.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.minispring.framework.proxy.Proxy;
import com.minispring.framework.proxy.ProxyChain;
import com.minispring.framework.proxy.ProxyManager;

import lombok.Data;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyManagerTest {
    
    @Test
    public void testCreateProxy() {
        ProxyManager manager = new ProxyManager();
        User user = new User();
        UserProxy proxyA = new UserProxy();
        proxyA.setCount(1);
        UserProxy proxyB = new UserProxy();
        proxyB.setCount(2);
        List<Proxy> proxyList = new ArrayList<Proxy>();
        proxyList.add(proxyA);
        
        
        manager.createProxy(User.class, proxyList);
    }
    
    @Data
    class User {
        private String name;
        
        private Integer age;
        
        public void show() {
            System.out.println("name: " + name + " age " + age);
        }
    }
    
    @Data
    class UserProxy implements Proxy{
        private Integer count = 0;

        
        
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("before UserProxy " + count);
            proxy.invokeSuper(obj, args);
            System.out.println("after UserProxy");
            return null;
        }



        @Override
        public Object doProxy(ProxyChain proxyChain) throws Throwable {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
}
