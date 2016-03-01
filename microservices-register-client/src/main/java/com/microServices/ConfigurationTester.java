package com.microservices;


import com.microservices.utils.Preconditions;
import com.microservices.model.App;
import com.microservices.model.Register;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Configuration Tester
 * Verify content of application.yml file
 */
@Component
public class ConfigurationTester {

    @Inject
    Register register;

    @Inject
    App app;

    public ConfigurationTester() {
    }

    void testConfiguration() {
        testAppConfiguration();
        testRegisterConfiguration();
    }

    void testRegisterConfiguration() {
        String msg = "Register %s can't be null, please check your config";
        Preconditions.checkNotNull(register.getHostName(),msg,"hostname");
        Preconditions.checkNotNull(register.getPort(),msg,"port");
    }

    void testAppConfiguration() {
        String msg = "App %s can't be null, please check your config";
        Preconditions.checkNotNull(app.getHostName(), msg,"hostname");
        Preconditions.checkNotNull(app.getPort(),msg,"port");
        Preconditions.checkNotNull(app.getPriority(),msg,"priority");
        Preconditions.checkNotNull(app.getInstanceID(),msg,"instanceID");
        Preconditions.checkNotNull(app.getApp(),msg,"appID");
        Preconditions.checkNotNull(app.getPath(),msg,"path");
    }
}