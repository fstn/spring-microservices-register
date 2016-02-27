package com.microServices.model;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EndPoint)) return false;

        EndPoint endPoint = (EndPoint) o;

        if (getPath() != null ? !getPath().equals(endPoint.getPath()) : endPoint.getPath() != null) return false;
        return getMethod() != null ? getMethod().equals(endPoint.getMethod()) : endPoint.getMethod() == null;

    }

    @Override
    public int hashCode() {
        int result = getPath() != null ? getPath().hashCode() : 0;
        result = 31 * result + (getMethod() != null ? getMethod().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EndPoint{" +
                "path='" + path + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}