package com.microservices.rest;

import com.microservices.RegisterClient;
import com.microservices.RestRegisterHelper;
import com.microservices.model.EntityInvoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by stephen on 27/02/2016.
 */

@Component
@Path("/")
public class RestController {
    private static final Logger logger = LoggerFactory.getLogger(RestController.class);

    @Inject
    RegisterClient<EntityInvoice> registerClient;

    /**
     * Test children  inheritance service
     * @param entity
     * @return
     */
    @POST
    @Path("validate")
    @Consumes("application/json")
    @Produces("application/json")
    public EntityInvoice validate(EntityInvoice entity) {
        entity = new RestRegisterHelper<EntityInvoice>(registerClient){
            @Override
            public EntityInvoice run(EntityInvoice entity) {
                ((EntityInvoice)entity).getData().getDynamicField().put("TVA_INTRACOM",10.5);
                return entity;
            }
        }.execute(entity);
        return entity;
    }

    /**
     * Test children  inheritance service with stop on first child
     * @param entity
     * @return
     */
    @POST
    @Path("stopAll")
    @Consumes("application/json")
    @Produces("application/json")
    public EntityInvoice stopAll(EntityInvoice entity) {

        entity.setStopAll(true);
        entity = new RestRegisterHelper<EntityInvoice>(registerClient){
            @Override
            public EntityInvoice run(EntityInvoice entity) {
                ((EntityInvoice)entity).getData().getDynamicField().put("TVA_INTRACOM",10.5);
                return entity;
            }
        }.execute(entity);
        return entity;
    }

    /**
     * Test children  inheritance service with stop all on first child
     * @param entity
     * @return
     */
    @POST
    @Path("stopChildren")
    @Consumes("application/json")
    @Produces("application/json")
    public EntityInvoice stopChildren(EntityInvoice entity) {
        entity.setStopChildren(true);
        entity = new RestRegisterHelper<EntityInvoice>(registerClient){
            @Override
            public EntityInvoice run(EntityInvoice entity) {
                ((EntityInvoice)entity).getData().getDynamicField().put("TVA_INTRACOM",10.5);
                return entity;
            }
        }.execute(entity);
        return entity;
    }


    /**
     * Test children  inheritance service with error on first child
     * @param entity
     * @return
     */
    @POST
    @Path("errorOnChild")
    @Consumes("application/json")
    @Produces("application/json")
    public EntityInvoice errorOnChild(EntityInvoice entity) {
        entity = new RestRegisterHelper<EntityInvoice>(registerClient){
            @Override
            public EntityInvoice run(EntityInvoice entity) {
                throw new NullPointerException();
            }
        }.execute(entity);
        return entity;
    }
}