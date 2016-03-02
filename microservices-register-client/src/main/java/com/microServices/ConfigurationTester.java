package com.microservices;


import com.microservices.model.application.Application;
import com.microservices.model.Config;
import com.microservices.model.Register;
import com.microservices.utils.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

/**
 * Created by stephen on 27/02/2016.
 * Configuration Tester
 * Verify content of application.yml file
 */
@Named
public class ConfigurationTester {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationTester.class);

    public ConfigurationTester() {
    }

    /**
     * Check all properties files
     * @param app
     * @param register
     * @param config
     */
    public void checkConfiguration(Application app, Register register, Config config) {
        logger.debug("ConfigurationTester:checkConfiguration");
        checkApplicationConfiguration(app);
        checkRegisterConfiguration(register);
        checkConfigConfiguration(config);
    }

    /**
     * Check Register configuration
     * @param register
     */
    public void checkRegisterConfiguration(Register register) {
        logger.debug("ConfigurationTester:checkRegisterConfiguration");
        String msg = "Register %s can't be null, please check your config";
        Preconditions.checkNotNull(register.getHostName(), msg, "hostname");
        Preconditions.checkNotNull(register.getPort(), msg, "port");
    }

    /**
     * Check Application configuration
     * @param app
     */
    void checkApplicationConfiguration(Application app) {
        logger.debug("ConfigurationTester:checkApplicationConfiguration");
        String msg = "Application %s can't be null, please check your config";
        Preconditions.checkNotNull(app.getHostName(), msg, "hostname");
        Preconditions.checkNotNull(app.getPort(), msg, "port");
        Preconditions.checkNotNull(app.getPriority(), msg, "priority");
        Preconditions.checkNotNull(app.getInstanceId(), msg, "instanceID");
        Preconditions.checkNotNull(app.getId(), msg, "appID");
        Preconditions.checkNotNull(app.getPath(), msg, "path");
    }

    /**
     * Check Config Configuration
     * @param config
     */
    public void checkConfigConfiguration(Config config) {
        logger.debug("ConfigurationTester:checkConfigConfiguration");
        String msg = "Config %s can't be null, please check your config";
        Preconditions.checkNotNull(config.getReloadChildrenDelay(), msg, "reloadChildrenDelay");
    }
}