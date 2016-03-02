package com.microservices.model.stackTrace;

import com.microservices.model.application.Application;
import com.microservices.model.application.ApplicationInsideStackTrace;
import com.microservices.model.application.EndPoint;

/**
 * Created by SZA on 01/03/2016.
 * Call error representation
 */
public class StackTraceWSError {

    private ApplicationInsideStackTrace app;
    private EndPoint endPoint;
    private String exception;

    public StackTraceWSError(){
    }

    public StackTraceWSError(Application app, EndPoint endPoint) {
        this.app = new ApplicationInsideStackTrace(app);
        this.endPoint = endPoint;
    }

    public StackTraceWSError(Application app, EndPoint endPoint, Exception e) {
        this.app = new ApplicationInsideStackTrace(app);
        this.endPoint = endPoint;
        this.exception = e.getMessage()+" "+e.getCause().getMessage();
    }

    public ApplicationInsideStackTrace getApp() {
        return app;
    }

    public void setApp(ApplicationInsideStackTrace app) {
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
