package com.microservices.model;

import com.microservices.StatusType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.ws.rs.HttpMethod;
import java.util.ArrayList;
import java.util.List;

/**
 * App representation
 */

@Component
@ConfigurationProperties(prefix = "app")
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


}