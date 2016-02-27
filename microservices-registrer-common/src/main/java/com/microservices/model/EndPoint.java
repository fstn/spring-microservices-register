package com.microServices.model;


/**
 * Created by SZA on 26/02/2016.
 */

import java.util.Enumeration;

/**
 * WebService EndPoint representation
 */
public class EndPoint {

    public enum  ExecutePosition {
        BEFORE,AFTER, PARENT;
    }
    /**
     * EndPoint path
     * @exemple /invoice
     * ignore / at begin and end
     */
    private String path;
    /**
     * EndPoint method
     * refer to HttpMethod
     */
    private String method;

    /**
     * Allow to choose when call the service
     */
    private ExecutePosition executePosition;

    public EndPoint() {
        super();
    }

    public EndPoint(String path, String method, ExecutePosition executePosition) {
        setPath(path);
        this.executePosition = executePosition;
        this.method = method;
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
        path = path.startsWith("/") ? path.substring(1) : path;
        path = path.endsWith("/") ? path.substring(path.length()-1) : path;
        this.path = path;
    }

    public ExecutePosition getExecutePosition() {
        return executePosition;
    }

    public void setExecutePosition(ExecutePosition executePosition) {
        this.executePosition = executePosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EndPoint)) return false;

        EndPoint endPoint = (EndPoint) o;

        if (getPath() != null ? !getPath().equals(endPoint.getPath()) : endPoint.getPath() != null) return false;
        if (getMethod() != null ? !getMethod().equals(endPoint.getMethod()) : endPoint.getMethod() != null)
            return false;
        return getExecutePosition() == endPoint.getExecutePosition();

    }

    @Override
    public int hashCode() {
        int result = getPath() != null ? getPath().hashCode() : 0;
        result = 31 * result + (getMethod() != null ? getMethod().hashCode() : 0);
        result = 31 * result + (getExecutePosition() != null ? getExecutePosition().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EndPoint{" +
                "path='" + path + '\'' +
                ", method='" + method + '\'' +
                ", executePosition=" + executePosition +
                '}';
    }
}