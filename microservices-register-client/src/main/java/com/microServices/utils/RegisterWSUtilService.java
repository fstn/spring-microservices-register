package com.microservices.utils;

import com.microservices.model.App;
import com.microservices.model.Register;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;

@Component
@Singleton
public class RegisterWSUtilService {

    @Inject
    App currentApp;

    @Inject
    Register register;

    public RegisterWSUtilService() {
    }

    public ClientResponse callUnregisterWS() {
        Client client = Client.create();

        WebResource webResource = client
                .resource("http://" + register.getHostName() + ":"
                        + register.getPort() + "/apps/" + currentApp.getApp());

        return webResource
                .delete(ClientResponse.class);
    }

    public ClientResponse callHeartBeatWS() {
        Client client = Client.create();

        WebResource webResource = client
                .resource("http://" + register.getHostName() + ":"
                        + register.getPort() + "/apps/" + currentApp.getApp() + "/" + currentApp.getInstanceID());

        return webResource
                .put(ClientResponse.class);
    }

    public ClientResponse callChildrenWS() {
        Client client = Client.create();

        WebResource webResource = client
                .resource("http://" + register.getHostName() + ":"
                        + register.getPort() + "/apps/" + currentApp.getApp() + "/" + currentApp.getInstanceID() + "/children");

        return webResource
                .get(ClientResponse.class);
    }
}