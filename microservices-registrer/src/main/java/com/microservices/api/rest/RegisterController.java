package com.microServices.api.rest;

import com.microServices.facade.DirectoryFacade;
import com.microServices.model.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by SZA on 26/02/2016.
 */
@Component
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
    public void registerApps(@PathParam("appID") String appID,App app) {
        logger.info("Register "+app);
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
    public void unRegisterApps(@PathParam ("appID") String appID, @PathParam("instanceID") String instanceID) {
        logger.info("UnRegister "+appID+":"+appID);
    }

    /**
     * Send application instance heartbeat
     *
     * @param appID application ID
     * @param instanceID instance ID
     */
    @PUT
    @Path("/{appID}/{instanceID}")
    @Consumes("application/json")
    @Produces("application/json")
    public Optional<App> heartBeatApps(@PathParam("appID") String appID, @PathParam("instanceID") String instanceID) {
        logger.info("HeartBeat "+appID+":"+instanceID);
        return directoryFacade.findRegisteredByIdAndInstanceId(appID,instanceID);
    }

    /**
     * Return application
     *
     * @param appID application ID
     */
    @GET
    @Path("/{appID}")
    @Produces("application/json")
    public Optional<App> getApps(@PathParam("appID") String appID) {
        logger.info("Get app  with ID "+appID);
        return directoryFacade.findRegisteredById(appID);

    }

    /**
     * Return application child endpoints
     *
     * @param appID application ID
     * @param instanceID instance ID
     */
    @GET
    @Path("/{appID}/{instanceID}/children")
    @Produces("application/json")
    public List<App> getInstanceChildren(@PathParam("appID") String appID, @PathParam("instanceID") String instanceID) {
        logger.info("Get children for app  with ID "+appID+":"+instanceID);
        return directoryFacade.findRegisteredChildrenById(appID);
    }

    /**
     * Return application
     *
     * @return list of apps
     */
    @GET
    @Path("/")
    @Produces("application/json")
    public List<App> getApps() {
        logger.info("Get all apps ");
        return directoryFacade.getAllRegistered();

    }
}
