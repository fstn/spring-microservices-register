package com.microServices.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;

/**
 * App representation yml
 */

@Component
@ConfigurationProperties(locations = "classpath:register.yml", prefix = "app")
@XmlRootElement
public class App {

    /**
     * App name
     * <p>
     * Example world, fr, eur ..
     */
    private String app;
    /**
     * App context path
     * <p>
     * Example /app
     */
    private String path;
    /**
     * Parent app name
     * <p>
     * Example world, fr, eur ..
     */
    private String parentApp;
    /**
     * Hostname
     * <p>
     * Example localhost
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
    /**
     * App last update
     */
    private Date lastUpdate;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        path = path.startsWith("/") ? path.substring(1) : path;
        path = path.endsWith("/") ? path.substring(path.length() - 1) : path;
        this.path = path;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof App)) return false;

        App app1 = (App) o;

        return getApp() != null ? getApp().equals(app1.getApp()) : app1.getApp() == null && (getInstanceID() != null ? getInstanceID().equals(app1.getInstanceID()) : app1.getInstanceID() == null);

    }

    @Override
    public int hashCode() {
        int result = getApp() != null ? getApp().hashCode() : 0;
        result = 31 * result + (getInstanceID() != null ? getInstanceID().hashCode() : 0);
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