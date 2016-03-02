package com.microservices.model;


import com.microservices.model.application.Application;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 27/02/2016.
 * Directory that keep all registered app
 */
@Singleton
@Named
public class Directory {
    private List<Application> registeredApp;

    @PostConstruct
    private void init() {
        registeredApp = new ArrayList<Application>();
    }

    public List<Application> getRegisteredApps() {
        return registeredApp;
    }

    public void setRegisteredApp(List<Application> registeredApp) {
        this.registeredApp = registeredApp;
    }

    @Override
    public String toString() {
        return "Directory{" +
                "registeredApp=" + registeredApp +
                '}';
    }
}
