package com.minispring.framework.bean;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class Param {
    private Map<String, Object> paramMap;
    
    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
    
    public Integer getInteger(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        
        Object obj = paramMap.get(name);
        
        if (obj != null) {
            return Integer.valueOf(String.valueOf(obj));
        }
        return null;
    }
    
    public Long getLong(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        
        Object obj = paramMap.get(name);
        
        if (obj != null) {
            return Long.valueOf(String.valueOf(obj));
        }
        return null;
    }
}
