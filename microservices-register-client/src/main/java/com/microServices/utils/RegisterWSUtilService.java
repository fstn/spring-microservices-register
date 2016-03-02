package com.microservices.utils;

import com.microservices.model.App;
import com.microservices.model.Register;
import com.microservices.producer.AppProducer;
import com.microservices.producer.RegisterProducer;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Named
@Singleton
public class RegisterWSUtilService {

    @Inject
    AppProducer appProducer;

    @Inject
    RegisterProducer registerProducer;

    private App currentApp;
    private Register register;

    private static final Logger logger = LoggerFactory.getLogger(RegisterWSUtilService.class);


    @PostConstruct
    public void init(){
        currentApp = appProducer.get();
        register = registerProducer.get();
    }

    public RegisterWSUtilService() {
    }

    public Response callUnregisterWS() {

        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
        WebTarget target = client.target("http://" + register.getHostName() + ":"
                + register.getPort() );
        target = target.path("/apps/" + currentApp.getId());

        logger.info(target.getUri().toString());
        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE);

        return builder.delete();
    }

    public Object callChildrenWS(Class type) {

        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
        WebTarget target = client.target("http://" + register.getHostName() + ":"
                + register.getPort() );
        target = target.path( "/apps/" + currentApp.getId() + "/" + currentApp.getInstanceId() + "/children");

        logger.info(target.getUri().toString());
        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE);

        return builder.get(type);
    }
}