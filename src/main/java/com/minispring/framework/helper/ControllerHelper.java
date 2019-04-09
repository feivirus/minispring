package com.minispring.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.minispring.framework.annotation.Action;
import com.minispring.framework.bean.Handler;
import com.minispring.framework.bean.Request;

public final class ControllerHelper {
    
    final static Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();
    
    static {
      
        Set<Class<?>> controllerSet = ClassHelper.getControllerClassSet();
        
        if (CollectionUtils.isNotEmpty(controllerSet)) {
            for(Class<?> clazz : controllerSet) {
                Method[] methodList = clazz.getDeclaredMethods();
                
                if (ArrayUtils.isNotEmpty(methodList)) {
                    
                    for(Method method : methodList) {
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String actionPath = action.value();
                            
                            if (actionPath.contains(":")) {
                                String[] pathArray = actionPath.split(":");
                                
                                if (pathArray != null && pathArray.length == 2) {
                                    String requestMethod = pathArray[0];
                                    String requestPath = pathArray[1];
                                    
                                    Request request = new Request(requestMethod, requestPath);
                                    
                                    Handler handler = new Handler(clazz, method);
                                    ACTION_MAP.put(request, handler);
                                }                                
                            }
                            
                        }
                    }
                }
            }
        }        
    }
    
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        
        return ACTION_MAP.get(request);
    }
}
