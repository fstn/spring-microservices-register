package com.microservices;

import com.microservices.api.rest.RegisterController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by SZA on 26/02/2016.
 */
@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(RegisterController.class);
    }
}
