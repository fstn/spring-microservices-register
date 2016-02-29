package com.microservices.model;

/**
 * Created by stephen on 27/02/2016.
 */
public class StackTraceWSElement {

    private App app;
    private EndPoint endPoint;

    public StackTraceWSElement(App app, EndPoint endPoint) {
        this.app = app;
        this.endPoint = endPoint;
    }

    public StackTraceWSElement() {
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
