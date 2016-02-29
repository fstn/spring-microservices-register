package com.microservices.model;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 27/02/2016.
 * Directory that keep all registered app
 */
@Singleton
@Component
public class Directory {
    private List<App> registeredApp;

    @PostConstruct
    private void init() {
        registeredApp = new ArrayList<App>();
    }

    public List<App> getRegisteredApps() {
        return registeredApp;
    }

    public void setRegisteredApp(List<App> registeredApp) {
        this.registeredApp = registeredApp;
    }

    @Override
    public String toString() {
        return "Directory{" +
                "registeredApp=" + registeredApp +
                '}';
    }
}
