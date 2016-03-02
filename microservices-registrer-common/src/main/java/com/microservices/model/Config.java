package com.microservices.model;

import javax.inject.Named;

/**
 * Created by stephen on 28/02/2016.
 * Register config class
 */
@Named
public class Config {

    /**
     * Cleaning cron fixed delay in millisecond
     */
    private Integer cleaningDelay;
    /**
     * Registered app max TTL in millisecond
     */
    private Integer appTTL;
    /**
     * reload children delay
     */
    private Integer reloadChildrenDelay;

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

    public void setReloadChildrenDelay(Integer reloadChildrenDelay) {
        this.reloadChildrenDelay = reloadChildrenDelay;
    }

    public Integer getReloadChildrenDelay() {
        return reloadChildrenDelay;
    }
}
