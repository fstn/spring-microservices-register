package com.microservices.model;

import javax.inject.Named;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * App representation yml
 */

@Named
@XmlRootElement
public class App {

    /**
     * App name
     * <p/>
     * Example world, fr, eur ..
     */
    private String id;
    /**
     * App context path
     * <p/>
     * Example /id
     */
    private String path;
    /**
     * Parent id name
     * <p/>
     * Example world, fr, eur ..
     */
    private String parentApp;
    /**
     * Hostname
     * <p/>
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
     * App instanceId (use if multiple service deployed with same instanceId)
     */
    private String instanceId;
    /**
     * App available endPoints
     */
    private List<EndPoint> endPoints;
    /**
     * App last update
     */
    private Date lastUpdate;

    /**
     * Method endPoint priority
     */

    public App() {

        super();
        this.endPoints = new ArrayList<>();
    }

    private Double priority;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public List<EndPoint> getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(List<EndPoint> endPoints) {
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

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof App)) return false;

        App app1 = (App) o;

        return getId() != null ? getId().equals(app1.getId()) : app1.getId() == null && (getInstanceId() != null ? getInstanceId().equals(app1.getInstanceId()) : app1.getInstanceId() == null);

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getInstanceId() != null ? getInstanceId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "App{" +
                "id='" + id + '\'' +
                ", parentApp='" + parentApp + '\'' +
                ", hostName='" + hostName + '\'' +
                ", status=" + status +
                ", port=" + port +
                ", instanceId='" + instanceId + '\'' +
                ", endPoints=" + endPoints +
                '}';
    }
}