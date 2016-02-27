package com.microServices.rest;

import com.microServices.RegisterClient;
import com.microServices.model.EndPoint;
import com.microServices.model.Entity;
import com.microServices.model.Invoice;
import com.sun.jersey.api.client.GenericType;
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
    RegisterClient registerClient;

    @POST
    @Path("validate")
    @Consumes("application/json")
    @Produces("application/json")
    public Entity<Invoice> validate(Entity<Invoice> entity) {
        entity  = (Entity)registerClient.executeOnChildren(new EndPoint("validate", HttpMethod.POST,
                        EndPoint.ExecutePosition.BEFORE),
                entity,
                new GenericType<Entity<Invoice>>(){});
        logger.info("Validate");
        entity.getData().getDynamicField().put("TVA_INTRACOM",10.5);
        entity = registerClient.addStackCall(entity,new EndPoint("validate", HttpMethod.POST,
                EndPoint.ExecutePosition.PARENT));
        entity  = (Entity)registerClient.executeOnChildren(new EndPoint("validate", HttpMethod.POST,EndPoint.ExecutePosition.AFTER),
                entity,
                new GenericType<Entity<Invoice>>(){});
        return entity;
    }

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

}