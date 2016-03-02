package com.microservices;


import com.microservices.model.App;
import com.microservices.model.Config;
import com.microservices.model.Register;
import com.microservices.utils.Preconditions;

import javax.inject.Named;

/**
 * Configuration Tester
 * Verify content of application.yml file
 */
@Named
public class ConfigurationTester {


    public ConfigurationTester() {
    }

    void testConfiguration(App app, Register register, Config config) {
        testAppConfiguration(app);
        testRegisterConfiguration(register);
        testConfigConfiguration(config);
    }

    public void testRegisterConfiguration(Register register) {
        String msg = "Register %s can't be null, please check your config";
        Preconditions.checkNotNull(register.getHostName(), msg, "hostname");
        Preconditions.checkNotNull(register.getPort(), msg, "port");
    }

    void testAppConfiguration(App app) {
        String msg = "App %s can't be null, please check your config";
        Preconditions.checkNotNull(app.getHostName(), msg, "hostname");
        Preconditions.checkNotNull(app.getPort(), msg, "port");
        Preconditions.checkNotNull(app.getPriority(), msg, "priority");
        Preconditions.checkNotNull(app.getInstanceId(), msg, "instanceID");
        Preconditions.checkNotNull(app.getId(), msg, "appID");
        Preconditions.checkNotNull(app.getPath(), msg, "path");
    }

    public void testConfigConfiguration(Config config) {
        String msg = "Config %s can't be null, please check your config";
        Preconditions.checkNotNull(config.getReloadChildrenDelay(), msg, "reloadChildrenDelay");
    }
}