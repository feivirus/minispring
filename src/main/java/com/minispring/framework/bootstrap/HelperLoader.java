package com.minispring.framework.bootstrap;

import com.minispring.framework.helper.BeanHelper;
import com.minispring.framework.helper.ClassHelper;
import com.minispring.framework.helper.ControllerHelper;
import com.minispring.framework.helper.IocHelper;
import com.minispring.framework.util.ClassUtil;

public final class HelperLoader {
    
    public static void init() {
        Class<?>[] classList = {ClassHelper.class, BeanHelper.class, IocHelper.class, ControllerHelper.class};
        
        for(Class<?> clazz: classList) {
            ClassUtil.loadClass(clazz.getName());
        }
    }
    
    public static void main(String[] args) {
        init();
    }
}
