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

    @POST
    @Path("validate")
    @Consumes("application/json")
    @Produces("application/json")
    public EntityInvoice validate(EntityInvoice entity) {

        entity = new RestRegisterHelper<EntityInvoice>(registerClient) {
            @Override
            public EntityInvoice run(EntityInvoice entity) {
                entity.getData().getDynamicField().put("WWW", "www.test.com");
                return entity;
            }
        }.execute(entity);
        return entity;

    }
}

