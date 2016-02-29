package com.microservices.utils;

import com.microservices.RegisterInit;
import com.microservices.model.App;
import com.microservices.model.EndPoint;
import com.microservices.model.Register;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by stephen on 27/02/2016.
 */
@Component
public class RegisterUtilService<T> {
    private static final Logger logger = LoggerFactory.getLogger(RegisterUtilService.class);

    @Inject
    public com.microservices.utils.RegisterWSUtilService registerWSUtilsService;

    @Inject
    public App currentApp;

    @Inject
    public Register register;

    @Inject
    private ChildrenWSUtilService<T> childrenWSUtilsService;
    private List<App> childrenApp;
    private EndPoint endPoint;
    private Object param;
    private GenericType type;

    /**
     * Call this to register your service
     * Required register.yml file inside your resource folder
     */
    public void register(){
        logger.debug("Register");
        RegisterInit registerInit = new RegisterInit(currentApp,register);
        registerInit.start();
    }

    /**
     * Call this to unRegister your service
     * Required register.yml file inside your resource folder
     */
    public void unregister() {
        logger.debug("Unregister");
        ClientResponse response = registerWSUtilsService.callUnregisterWS();

        if (!isOk(response)) {
            throw new RuntimeException("Unable to unRegister application "+currentApp.getApp());
        }

    }


    /**
     * Call this to heartBeat your service
     * Required register.yml file inside your resource folder
     */
    public void heartBeat() {
        logger.debug("HeartBeat");

        ClientResponse response = registerWSUtilsService.callHeartBeatWS();

        if (!isOk(response)) {
            throw new RuntimeException("Unable to heartBeat application "+currentApp.getApp());
        }else{

        }
    }


    /**
     * Call this to get app your service's children
     * Required register.yml file inside your resource folder
     *
     * @return
     */
    public List<App> getChildren() {
        logger.debug("Get children");
              List<App> result = null;
        ClientResponse response = registerWSUtilsService.callChildrenWS();

        if (isOk(response)) {
            result = response.getEntity(new GenericType<List<App>>(){});
        }else{
            throw new RuntimeException("Unable to heartBeat application "+currentApp.getApp()+" "+response.getStatus());
        }
        return result;
    }


    /**
     * Call this to execute endPoint on your service's children
     * Required register.yml file inside your resource folder
     *
     * @param childrenApp
     * @param endPoint
     * @param param
     * @return
     */
    public T executeOnChildren(List<App> childrenApp, EndPoint endPoint, T param) {
        this.childrenApp = childrenApp;
        this.endPoint = endPoint;
        this.param = param;
        logger.debug("Call child "+childrenApp+", "+endPoint);
        T result = param;
        for(App app:childrenApp) {
            // execute only on child that have same endPoints
            if(app.getEndPoints().contains(endPoint)) {
                ClientResponse response = childrenWSUtilsService.executeOnChildrenWS(app, endPoint, result);

                if (isOk(response)) {
                    LinkedHashMap<String,Object> genericResult = response.getEntity(new GenericType<LinkedHashMap<String,Object>>(){});
                    ObjectMapper mapper = new ObjectMapper();
                    result =
                            mapper.readValue(genericResult, new TypeReference<result.>(){});


                } else {
                    throw new RuntimeException("Unable to execute endPoint on children application " + app.getApp() + " " + endPoint + " " + response.getStatus());
                }
            }
        }
        return result;
    }

    private boolean isOk(ClientResponse response) {
        return response.getStatus() == 200 || response.getStatus() == 204;
    }


}
