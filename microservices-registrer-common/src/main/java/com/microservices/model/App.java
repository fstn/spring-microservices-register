package com.microServices.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * App representation
 */

@Component
@ConfigurationProperties(locations = "classpath:register.yml",prefix = "app")
@XmlRootElement
public class App {

    /**
     * App name
     *
     * @example world, fr, eur ..
     */
    private String app;
    /**
     * Parent app name
     *
     * @example world, fr, eur ..
     */
    private String parentApp;
    /**
     * Hostname
     *
     * @example localhost
     */
    private String hostName;
    /**
     * App status
     */
    private StatusType status;
    /**
     * App port
     */
    private Integer port;
    /**
     * App instanceID (use if multiple service deployed with same instanceID)
     */
    private String instanceID;
    /**
     * App available endPoints
     */
    private ArrayList<EndPoint> endPoints;

    public App() {
        super();
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getParentApp() {
        return parentApp;
    }

    public void setParentApp(String parentApp) {
        this.parentApp = parentApp;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }

    public ArrayList<EndPoint> getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(ArrayList<EndPoint> endPoints) {
        this.endPoints = endPoints;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof App)) return false;

        App app1 = (App) o;

        if (getApp() != null ? !getApp().equals(app1.getApp()) : app1.getApp() != null) return false;
        if (getParentApp() != null ? !getParentApp().equals(app1.getParentApp()) : app1.getParentApp() != null)
            return false;
        if (getHostName() != null ? !getHostName().equals(app1.getHostName()) : app1.getHostName() != null)
            return false;
        if (getStatus() != app1.getStatus()) return false;
        if (getPort() != null ? !getPort().equals(app1.getPort()) : app1.getPort() != null) return false;
        if (getInstanceID() != null ? !getInstanceID().equals(app1.getInstanceID()) : app1.getInstanceID() != null)
            return false;
        return getEndPoints() != null ? getEndPoints().equals(app1.getEndPoints()) : app1.getEndPoints() == null;

    }

    @Override
    public int hashCode() {
        int result = getApp() != null ? getApp().hashCode() : 0;
        result = 31 * result + (getParentApp() != null ? getParentApp().hashCode() : 0);
        result = 31 * result + (getHostName() != null ? getHostName().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getPort() != null ? getPort().hashCode() : 0);
        result = 31 * result + (getInstanceID() != null ? getInstanceID().hashCode() : 0);
        result = 31 * result + (getEndPoints() != null ? getEndPoints().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "App{" +
                "app='" + app + '\'' +
                ", parentApp='" + parentApp + '\'' +
                ", hostName='" + hostName + '\'' +
                ", status=" + status +
                ", port=" + port +
                ", instanceID='" + instanceID + '\'' +
                ", endPoints=" + endPoints +
                '}';
    }
}