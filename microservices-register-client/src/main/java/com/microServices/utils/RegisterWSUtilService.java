package com.microservices.utils;

import com.microservices.model.App;
import com.microservices.model.Register;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Singleton
public class RegisterWSUtilService {

    @Inject
    App currentApp;

    @Inject
    Register register;

    public RegisterWSUtilService() {
    }

    public Response callUnregisterWS() {

        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
        WebTarget target = client.target("http://" + register.getHostName() + ":"
                + register.getPort() );
        target = target.path("/apps/" + currentApp.getApp());

        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE);

        return builder.delete();
    }

    public Object callChildrenWS(Class type) {

        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
        WebTarget target = client.target("http://" + register.getHostName() + ":"
                + register.getPort() );
        target = target.path( "/apps/" + currentApp.getApp() + "/" + currentApp.getInstanceID() + "/children");

        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE);

        return builder.get(type);
    }
}