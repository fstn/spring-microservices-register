package com.microservices;

import com.microservices.api.rest.RegisterController;
import org.glassfish.jersey.server.ResourceConfig;
import javax.inject.Named;

/**
 * Created by SZA on 26/02/2016.
 */
@Named
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(RegisterController.class);
    }
}
