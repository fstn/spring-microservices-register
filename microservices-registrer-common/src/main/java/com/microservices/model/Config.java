package com.microservices.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.inject.Named;

/**
 * Created by stephen on 28/02/2016.
 * Register config class
 */
@Named
@ConfigurationProperties(prefix = "config")
public class Config {

    /**
     * Cleaning cron fixed delay in millisecond
     */
    private Integer cleaningDelay;
    /**
     * Registered app max TTL in millisecond
     */
    private Integer appTTL;

    public Integer getCleaningDelay() {
        return cleaningDelay;
    }

    public void setCleaningDelay(Integer cleaningDelay) {
        this.cleaningDelay = cleaningDelay;
    }

    public Integer getAppTTL() {
        return appTTL;
    }

    public void setAppTTL(Integer appTTL) {
        this.appTTL = appTTL;
    }
}
