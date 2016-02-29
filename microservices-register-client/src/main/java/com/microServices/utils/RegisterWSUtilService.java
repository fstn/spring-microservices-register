package com.microservices.utils;

import com.microservices.model.App;
import com.microservices.model.Register;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
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

        Client client = ClientBuilder.newBuilder().newClient();
        WebTarget target = client.target("http://" + register.getHostName() + ":"
                + register.getPort() );
        target = target.path("/apps/" + currentApp.getApp());

        Invocation.Builder builder = target.request();

        return builder.delete();
    }

    public Response callChildrenWS() {

        Client client = ClientBuilder.newBuilder().newClient();
        WebTarget target = client.target("http://" + register.getHostName() + ":"
                + register.getPort() );
        target = target.path( "/apps/" + currentApp.getApp() + "/" + currentApp.getInstanceID() + "/children");

        Invocation.Builder builder = target.request();

        return builder.get();
    }
}