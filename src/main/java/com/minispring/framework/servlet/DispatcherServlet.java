package com.minispring.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.minispring.framework.bean.Data;
import com.minispring.framework.bean.Handler;
import com.minispring.framework.bean.Param;
import com.minispring.framework.bean.View;
import com.minispring.framework.bootstrap.HelperLoader;
import com.minispring.framework.helper.BeanHelper;
import com.minispring.framework.helper.ConfigHelper;
import com.minispring.framework.helper.ControllerHelper;
import com.minispring.framework.util.ReflectionUtil;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        
        if (handler != null) {
            Class<?> controllerClass = handler.getClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            Map<String, Object> parameterMap = new HashMap<String, Object>();            
            Enumeration<String> paramNames = req.getParameterNames();
            
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();
                Object paramValue = req.getParameter(paramName);
                
                if (paramValue != null) {
                    parameterMap.put(paramName, paramValue);
                }                
            }
            
            //TODO 处理body参数
            
            Param param = new Param(parameterMap);
            Method method = handler.getMethod();
            
            Object result = ReflectionUtil.invokeMethod(controllerBean, method, param);
            
            if (result instanceof View) {
                View view =  (View) result;
                String path = view.getPath();
                
                if (path.startsWith("/")) {
                    resp.sendRedirect(req.getContextPath() + path);
                } else {
                    Map<String, Object> model = param.getParamMap();
                    
                    for(Map.Entry<String, Object> entry : model.entrySet()) {
                        req.setAttribute(entry.getKey(), entry.getValue());
                    }
                    req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                }       
                
            } else if (result instanceof Data) {
                Data data = (Data)result;
                Object model = data.getModel();
                
                if (model != null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("utf-8");
                    PrintWriter writer = resp.getWriter();
                    String content = JSON.toJSONString(model);
                   writer.write(content);
                   writer.flush();
                    writer.close();
                }                
            }            
        }
        
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化ioc
        HelperLoader.init();
        
        //动态注册jsp的servlet
        ServletContext servletContext = config.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        
        //注册静态资源依赖的servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssertPath() + "*");     
        
    }
    
}
