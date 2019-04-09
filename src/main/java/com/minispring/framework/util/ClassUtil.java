package com.minispring.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minispring.framework.config.ConfigConstant;

public final class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);
    
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
    
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> clazz;
        
        try {
            clazz = Class.forName(className, isInitialized, getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return clazz;
    }
    
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }
    
    /**
     * 获取指定包下的所有类
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            
            while (urls.hasMoreElements()) {
                URL url = (URL) urls.nextElement();
                
                if (url.getProtocol().equals(ConfigConstant.FILE_PROTOCOL)) {
                    String packagePath = url.getPath().replace("20%", " ");
                    
                   addClass(classSet, packagePath, packageName);
                } else if (url.getProtocol().equals(ConfigConstant.JAR_PROTOCOL)) {
                    JarURLConnection connection = (JarURLConnection)url.openConnection();
                    JarFile jarFile = connection.getJarFile();
                    
                    if (jarFile != null) {
                       Enumeration<JarEntry> jarEntrys = jarFile.entries();
                       
                       while (jarEntrys.hasMoreElements()) {
                            JarEntry jarEntry = (JarEntry) jarEntrys.nextElement();
                            String jarEntryName = jarEntry.getName();
                        
                            if (jarEntryName.endsWith(".class")) {
                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                
                                doAddClass(classSet, className);
                            }
                       }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return classSet;
    }
    
    public static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {            
            public boolean accept(File pathname) {
                if (pathname.isFile() && pathname.getPath().endsWith(".class") || pathname.isDirectory()) {
                    return true;
                }
                return false;
            }
        });
        
        for(File file : files) {
            String fileName = file.getName();
            
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                
                if (StringUtils.isNotBlank(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                
                if (StringUtils.isNoneBlank(subPackagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                
                if (StringUtils.isNoneBlank(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }
    
    public static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> clazz = loadClass(className, false);
        
        classSet.add(clazz);
    }
}
