package com.microServices.utils;

import com.microServices.RegisterInit;
import com.microServices.model.App;
import com.microServices.model.EndPoint;
import com.microServices.model.Register;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by stephen on 27/02/2016.
 */
@Component
public class RegisterUtilService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterUtilService.class);

    @Inject
    public RegisterWSUtilService registerWSUtilsService;

    @Inject
    public App currentApp;

    @Inject
    public Register register;

    @Inject
    private ChildrenWSUtilService childrenWSUtilsService;
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
     * @param type
     * @return
     */
    public Object executeOnChildren(List<App> childrenApp, EndPoint endPoint, Object param, GenericType type) {
        this.childrenApp = childrenApp;
        this.endPoint = endPoint;
        this.param = param;
        this.type = type;
        logger.debug("Call child "+childrenApp+", "+endPoint);
        Object result = param;
        for(App app:childrenApp) {
            // execute only on child that have same endPoints
            if(app.getEndPoints().contains(endPoint)) {
                ClientResponse response = childrenWSUtilsService.executeOnChildrenWS(app, endPoint, result);

                if (isOk(response)) {
                    result = response.getEntity(type);
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
