package com.microServices.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by stephen on 27/02/2016.
 */

@Component
@ConfigurationProperties(locations = "classpath:register.yml",prefix = "register")
public class Register {
    /**
     * Hostname
     *
     * @example localhost
     */
    private String hostName;
    /**
     * Register port
     */
    private Integer port;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

}
