package com.microservices.utils;

import com.microservices.RegisterInit;
import com.microservices.model.App;
import com.microservices.model.AppListDTO;
import com.microservices.model.EndPoint;
import com.microservices.model.Register;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.core.Response;

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
    private App childApp;
    private EndPoint endPoint;
    private Object param;

    public RegisterUtilService() {
        super();
    }

    /**
     * Call this to register your service
     * Required register.yml file inside your resource folder
     */
    public void register() {
        logger.debug("Register");
        RegisterInit registerInit = new RegisterInit(currentApp, register);
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
            throw new RuntimeException("Unable to unRegister application " + currentApp.getApp());
        }

    }


    /**
     * Call this to get app your service's children
     * Required register.yml file inside your resource folder
     *
     * @return
     */
    public AppListDTO getChildren() {
        logger.debug("Get children");
        AppListDTO result = null;
        result = (AppListDTO) registerWSUtilsService.callChildrenWS(AppListDTO.class);
        /*if (isOk(response)) {

            JAXBContext jaxbContext = null;
            try {

                jaxbContext = JAXBContext.newInstance(List.class);
                List<App> myBean = (List<App>) jaxbContext.createUnmarshaller().unmarshal(response.get());
                result = (List<App>) response.readEntity(List.class);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Unable to getChildren application " + currentApp.getApp() + " " + response.getStatus());
        }*/
        return result;
    }


    /**
     * Call this to execute endPoint on your service's children
     * Required register.yml file inside your resource folder
     *
     * @param childApp
     * @param endPoint
     * @param param
     * @return
     */
    public T executeOnChild(App childApp, EndPoint endPoint, T param) {
        this.childApp = childApp;
        this.endPoint = endPoint;
        this.param = param;
        logger.debug("Call child " + childApp + ", " + endPoint);
        T result = param;
        // execute only on child that have same endPoints
        if (childApp.getEndPoints().contains(endPoint)) {
            try {
                result = childrenWSUtilsService.executeOnChildrenWS(childApp, endPoint, result);
            } catch (NotAcceptableException e) {
                throw new RuntimeException("Unable to execute endPoint on children application " + childApp.getApp() + " " + endPoint + " ", e);
            }
        }
        return result;
    }

    private boolean isOk(Response response) {
        return response.getStatus() == 200 || response.getStatus() == 204;
    }


}
