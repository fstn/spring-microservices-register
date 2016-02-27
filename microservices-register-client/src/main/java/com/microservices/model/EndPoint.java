package com.microservices.model;

import javax.ws.rs.HttpMethod;

/**
 * Created by SZA on 26/02/2016.
 */


public class EndPoint {
    private String path;
    private String method;

    public EndPoint() {
        super();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}