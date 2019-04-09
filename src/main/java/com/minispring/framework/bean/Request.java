package com.minispring.framework.bean;

import lombok.Data;

@Data
public class Request {
    String questMethod;
    
    String requestPath;
    
    public Request(String method, String path) {
        this.requestPath = path;
        this.questMethod = method;
    }
}
