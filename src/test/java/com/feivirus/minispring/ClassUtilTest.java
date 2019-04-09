package com.feivirus.minispring;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import com.minispring.framework.util.ClassUtil;

public class ClassUtilTest {
    @Test
    public void testGetClassSet() {
        Set<Class<?>> classSet =  ClassUtil.getClassSet("com.minispring.framework");
       
       Iterator<Class<?>> iterator = classSet.iterator();
       
       while (iterator.hasNext()) {
           Class<?> clazz = iterator.next();
           
           System.out.println(clazz.getName());
    }
    }
}
