package com.microservices;

import com.microservices.model.App;
import com.microservices.model.EndPoint;

/**
 * Created by SZA on 01/03/2016.
 * Call error representation
 */
public class StackTraceWSError {

    private App app;
    private EndPoint endPoint;
    private String exception;

    public StackTraceWSError(){

    }

    public StackTraceWSError(App app, EndPoint endPoint) {
        this.app = app;
        this.endPoint = endPoint;
    }

    public StackTraceWSError(App app, EndPoint endPoint, Exception e) {
        this.app = app;
        this.endPoint = endPoint;
        this.exception = e.getMessage();
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public EndPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(EndPoint endPoint) {
        this.endPoint = endPoint;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
