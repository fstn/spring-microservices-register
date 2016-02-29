package com.microservices.rest;

import com.microservices.RegisterClient;
import com.microservices.model.Entity;
import com.microservices.model.Invoice;
import com.microservices.RestRegisterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * Created by stephen on 27/02/2016.
 */

@Component
@Path("/")
public class RestController {
    private static final Logger logger = LoggerFactory.getLogger(RestController.class);

    @Inject
    RegisterClient<Entity<Invoice>> registerClient;

    @POST
    @Path("validate")
    @Consumes("application/json")
    @Produces("application/json")
    public Entity<Invoice> validate(Entity<Invoice> entity) {
        entity = new RestRegisterHelper<Entity<Invoice>>(registerClient){
            @Override
            public Entity run(Entity<Invoice> entity) {
                ((Entity<Invoice>)entity).getData().getDynamicField().put("TVA_INTRACOM",10.5);
                return entity;
            }
        }.execute(entity);
        return entity;
    }
/*
    @GET
    @Path("invoices")
    @Consumes("application/json")
    @Produces("application/json")
    public Invoice getInvoice() {
        logger.info("GetInvoices");
        Invoice invoice = (Invoice)registerClient.executeOnChildren(new EndPoint("invoices", HttpMethod.GET,EndPoint.ExecutePosition.AFTER),
                new Invoice(),
                new GenericType<Invoice>(){});
        return invoice;
    }
*/
}