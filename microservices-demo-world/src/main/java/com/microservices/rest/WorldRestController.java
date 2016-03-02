package com.microservices.rest;

import com.microservices.RegisterClient;
import com.microservices.RestRegisterHelper;
import com.microservices.model.EntityInvoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by stephen on 27/02/2016.
 */

@Named
@Path("/")
public class WorldRestController {
    private static final Logger logger = LoggerFactory.getLogger(WorldRestController.class);

    @Inject
    RegisterClient<EntityInvoice> registerClient;

    /**
     * Test children  inheritance service
     *
     * @param entity
     * @return
     */
    @POST
    @Path("validate")
    @Consumes("application/json")
    @Produces("application/json")
    public EntityInvoice validate(Object entity) {
        EntityInvoice resultEntity;
        resultEntity = new RestRegisterHelper<EntityInvoice>(registerClient, EntityInvoice.class) {
            @Override
            public EntityInvoice run(EntityInvoice entity) {
                ((EntityInvoice) entity).getData().setWww("www.invoice.com");
                return entity;
            }
        }.execute(entity);
        return resultEntity;
    }

    /**
     * Test children  inheritance service with stop on first child
     *
     * @param entity
     * @return
     */
    @POST
    @Path("stopAll")
    @Consumes("application/json")
    @Produces("application/json")
    public EntityInvoice stopAll(Object entity) {
        EntityInvoice resultEntity;
        resultEntity = new RestRegisterHelper<EntityInvoice>(registerClient, EntityInvoice.class) {
            @Override
            public EntityInvoice run(EntityInvoice entity) {
                ((EntityInvoice) entity).getData().setWww("www.invoice.com");
                return entity;
            }
        }.execute(entity);
        return resultEntity;
    }

    /**
     * Test children  inheritance service with stop all on first child
     *
     * @param entity
     * @return
     */
    @POST
    @Path("stopChildren")
    @Consumes("application/json")
    @Produces("application/json")
    public EntityInvoice stopChildren(Object entity) {
        EntityInvoice resultEntity;
        resultEntity = new RestRegisterHelper<EntityInvoice>(registerClient, EntityInvoice.class) {
            @Override
            public EntityInvoice run(EntityInvoice entity) {
                ((EntityInvoice) entity).getData().setWww("www.invoice.com");

                return entity;
            }
        }.execute(entity);
        return resultEntity;
    }

    /**
     * Test children  inheritance service with error on first child
     *
     * @param entity
     * @return
     */
    @POST
    @Path("errorOnChild")
    @Consumes("application/json")
    @Produces("application/json")
    public EntityInvoice errorOnChild(Object entity) {
        EntityInvoice resultEntity;
        resultEntity = new RestRegisterHelper<EntityInvoice>(registerClient, EntityInvoice.class) {
            @Override
            public EntityInvoice run(EntityInvoice entity) {
                ((EntityInvoice) entity).getData().setWww("www.invoice.com");

                return entity;
            }
        }.execute(entity);
        return resultEntity;
    }
}

