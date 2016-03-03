package com.microservices.model.application;


/**
 * Created by SZA on 26/02/2016.
 */

/**
 * WebService EndPoint representation
 */
public class EndPoint {

    /**
     * EndPoint path
     *
     * @exemple /invoice
     * ignore / at begin and end
     */
    private String path;
    /**
     * EndPoint method
     * refer to HttpMethod
     */
    private String method;


    public EndPoint() {
        super();
    }

    public EndPoint(String path, String method) {
        setPath(path);
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
        if (path != null) {
            path = path.startsWith("/") ? path.substring(1) : path;
            path = path.endsWith("/") ? path.substring(path.length() - 1) : path;
        }
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EndPoint endPoint = (EndPoint) o;

        if (path != null ? !path.equals(endPoint.path) : endPoint.path != null) return false;
        return method != null ? method.equals(endPoint.method) : endPoint.method == null;

    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
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