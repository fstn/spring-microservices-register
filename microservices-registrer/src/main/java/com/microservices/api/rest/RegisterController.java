package com.microservices.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

/**
 * Created by SZA on 26/02/2016.
 */
@Component
@Path("/apps")
public class RegisterController {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    /**
     * Register new application instance
     *
     * @param appID
     */
    @POST
    @Path("{appID}")
    @Consumes("application/json")
    @Produces("application/json")
    public void registerApps(@PathParam("appID") Integer appID) {

    }

    /**
     * UnRegister application instance
     *
     * @param appID
     */

    @DELETE
    @Path("{appID}/{instanceID}")
    @Consumes("application/json")
    @Produces("application/json")
    public void unRegisterApps(@PathParam ("appID") Integer appID, @PathParam("instanceID") Integer instanceID) {

    }

    /**
     * Send application instance heartbeat
     *
     * @param appID
     */
    @PUT
    @Path("{appID}")
    @Consumes("application/json")
    @Produces("application/json")
    public void heartBeatApps(@PathParam("appID") Integer appID) {

    }

    /**
     * Return application
     *
     * @param appID
     */
    @GET
    @Path("/{appID}")
    @Produces("application/json")
    public String getApps(@PathParam("appID") Integer appID) {
        return "ok";

    }
}
