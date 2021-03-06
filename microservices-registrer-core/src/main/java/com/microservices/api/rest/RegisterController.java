package com.microservices.api.rest;

import com.microservices.facade.DirectoryFacade;
import com.microservices.model.application.Application;
import com.microservices.dto.AppDTO;
import com.microservices.dto.AppListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;

/**
 * Created by SZA on 26/02/2016.
 */
@Named
@Path("/apps")
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Inject
    DirectoryFacade directoryFacade;

    /**
     * Register new application instance
     *
     * @param appID application ID
     */
    @POST
    @Path("/{appID}")
    @Consumes("application/json")
    @Produces("application/json")
    public void registerApps(@PathParam("appID") String appID, Application app) {
        logger.info("Register " + app);
        directoryFacade.register(app);
    }

    /**
     * UnRegister application instance
     *
     * @param appID application ID
     */

    @DELETE
    @Path("/{appID}/{instanceID}")
    @Consumes("application/json")
    @Produces("application/json")
    public void unRegisterApps(@PathParam("appID") String appID, @PathParam("instanceID") String instanceID) {
        logger.info("UnRegister " + appID + ":" + appID);
    }

    /**
     * Send application instance heartbeat
     *
     * @param appID      application ID
     * @param instanceID instance ID
     */
    @PUT
    @Path("/{appID}/{instanceID}")
    @Consumes("application/json")
    @Produces("application/json")
    public AppDTO heartBeatApps(@PathParam("appID") String appID, @PathParam("instanceID") String instanceID) {
        logger.info("HeartBeat " + appID + ":" + instanceID);
        return new AppDTO(directoryFacade.findRegisteredByIdAndInstanceId(appID, instanceID));
    }

    /**
     * Return application
     *
     * @param appID application ID
     */
    @GET
    @Path("/{appID}")
    @Produces("application/json")
    public AppDTO getApps(@PathParam("appID") String appID) {
        logger.info("Get app  with ID " + appID);
        return new AppDTO(directoryFacade.findRegisteredById(appID));

    }

    /**
     * Return application child endpoints
     *
     * @param appID      application ID
     * @param instanceID instance ID
     */
    @GET
    @Path("/{appID}/{instanceID}/children")
    @Produces("application/json")
    public AppListDTO getInstanceChildren(@PathParam("appID") String appID, @PathParam("instanceID") String instanceID) {
        logger.info("Get children for app  with ID " + appID + ":" + instanceID);
        return new AppListDTO(directoryFacade.findRegisteredChildrenById(appID));
    }

    /**
     * Return application
     *
     * @return list of apps
     */
    @GET
    @Path("/")
    @Produces("application/json")
    public AppListDTO getApps() {
        logger.info("Get all apps ");
        return new AppListDTO(directoryFacade.getAllRegistered());
    }
}
