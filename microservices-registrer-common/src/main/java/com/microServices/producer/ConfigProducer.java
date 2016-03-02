package com.microservices.producer;

import com.microservices.model.Config;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by SZA on 02/03/2016.
 */
@Named
public class ConfigProducer {

    private Config config;

    @PostConstruct
    public void init() throws IOException {
        Properties properties = new Properties();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        if (is != null) {
            properties.load(is);
            config = new Config();

            String appTTL = properties.getProperty("config.appTTL");
            if (appTTL != null)
                config.setAppTTL(Integer.parseInt(appTTL));

            String cleaningDelay = properties.getProperty("config.cleaningDelay");
            if (cleaningDelay != null)
                config.setCleaningDelay(Integer.parseInt(cleaningDelay));

            String reloadChildrenDelay = properties.getProperty("config.reloadChildrenDelay");
            if (reloadChildrenDelay != null)
                config.setReloadChildrenDelay(Integer.parseInt(reloadChildrenDelay));
        }

    }

    @Produces
    public Config get() {
        return config;
    }
}
