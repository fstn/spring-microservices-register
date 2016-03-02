package com.microservices.model.stackTrace;

import com.microservices.model.application.Application;
import com.microservices.model.application.ApplicationInsideStackTrace;
import com.microservices.model.application.EndPoint;

/**
 * Created by stephen on 27/02/2016.
 */
public class StackTraceWSElement {

    private ApplicationInsideStackTrace app;
    private EndPoint endPoint;


    public StackTraceWSElement(Application app, EndPoint endPoint) {
        this.app = new ApplicationInsideStackTrace(app);
        this.endPoint = endPoint;
    }

    public StackTraceWSElement() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StackTraceWSElement)) return false;

        StackTraceWSElement that = (StackTraceWSElement) o;

        if (getApp() != null ? !getApp().equals(that.getApp()) : that.getApp() != null) return false;
        return getEndPoint() != null ? getEndPoint().equals(that.getEndPoint()) : that.getEndPoint() == null;

    }

    @Override
    public int hashCode() {
        int result = getApp() != null ? getApp().hashCode() : 0;
        result = 31 * result + (getEndPoint() != null ? getEndPoint().hashCode() : 0);
        return result;
    }


}
