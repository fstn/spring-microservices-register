package com.microServices.utils;

import com.microServices.RegisterInit;
import com.microServices.model.App;
import com.microServices.model.Register;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by stephen on 27/02/2016.
 */
@Component
public class RegisterUtilService {

    @Inject
    App currentApp;

    @Inject
    Register register;

    /**
     * Call this to register your service
     * Required register.yml file inside your resource folder
     */
    public void register(){

        RegisterInit registerInit = new RegisterInit(currentApp,register);
        registerInit.start();
    }

    public void unregister() {
        Client client = Client.create();


        WebResource webResource = client
                .resource("http://"+register.getHostName()+":"
                        +register.getPort()+"/apps/"+currentApp.getApp());

        ClientResponse response = webResource
                .delete(ClientResponse.class);

        if (response.getStatus() != 200 && response.getStatus() != 204) {
            throw new RuntimeException("Unable to unRegister application "+currentApp.getApp());
        }

    }
}
