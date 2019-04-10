package com.minispring.framework.bean;

@lombok.Data
public class Data {
    private Object model;
    
    public Data(Object model) {
        this.model = model;
    }
}
