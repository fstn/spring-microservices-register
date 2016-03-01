package com.microservices.utils;

import com.microservices.RegisterInit;
import com.microservices.model.App;
import com.microservices.model.EndPoint;
import com.microservices.model.Register;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
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
        Response response = registerWSUtilsService.callUnregisterWS();

        if (!isOk(response)) {
            throw new RuntimeException("Unable to unRegister application "+currentApp.getApp());
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
        Response response = registerWSUtilsService.callChildrenWS();

        if (isOk(response)) {
            result =  (List<App>) response.readEntity(List.class);
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


                Response response = childrenWSUtilsService.executeOnChildrenWS(app, endPoint, result);

                if (isOk(response)) {
                    result = (T)response.getEntity();
                } else {
                    throw new RuntimeException("Unable to execute endPoint on children application " + app.getApp() + " " + endPoint + " " + response.getStatus());
                }
            }
        }
        return result;
    }

    private boolean isOk(Response response) {
        return response.getStatus() == 200 || response.getStatus() == 204;
    }


}
