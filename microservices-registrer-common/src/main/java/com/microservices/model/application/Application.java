package com.microservices.model.application;

import javax.inject.Named;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Application representation yml
 */

@Named
@XmlRootElement
public class Application {

    /**
     * Application name
     * <p/>
     * Example world, fr, eur ..
     */
    private String id;
    /**
     * Application context path
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
     * Application status
     */
    private StatusType status;
    /**
     * Application port
     */
    private Integer port;
    /**
     * Application instanceId (use if multiple service deployed with same instanceId)
     */
    private String instanceId;
    /**
     * Application available endPoints
     */
    private List<EndPoint> endPoints;
    /**
     * Application last update
     */
    private Date lastUpdate;

    /**
     * Method endPoint priority
     */

    public Application() {

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
        if (!(o instanceof Application)) return false;

        Application app1 = (Application) o;

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
        return "Application{" +
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