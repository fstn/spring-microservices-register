package com.microServices.rest;

import com.microServices.model.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by stephen on 27/02/2016.
 */

@Component
@Path("/world")
public class RestController {
    private static final Logger logger = LoggerFactory.getLogger(RestController.class);


    /**
     * Register new application instance
     */
    @POST
    @Path("/validateInvoice")
    @Consumes("application/json")
    @Produces("application/json")
    public void validateInvoice(Invoice invoice) {

    }
}
