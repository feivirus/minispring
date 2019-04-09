package com.minispring.framework.bean;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class View {
    private String path;
    
    private Map<String, Object> model;
    
    public View(String path) {
        this.path = path;
        if (model == null) {
            model = new HashMap<String, Object>();
        }
    }
    
    public void addModel(String key, Object value) {
        model.put(key, value);
    }
}
