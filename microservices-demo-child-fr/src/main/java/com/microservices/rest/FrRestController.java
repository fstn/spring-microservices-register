package com.microservices.rest;

import com.microservices.RegisterClient;
import com.microservices.RestRegisterHelper;
import com.microservices.model.FrEntityInvoice;
import com.microservices.model.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Date;

/**
 * Created by stephen on 27/02/2016.
 */

@Named
@Path("/")
public class FrRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrRestController.class);

    @Inject
    RegisterClient<FrEntityInvoice> registerClient;
    /**
     * Test children  inheritance service
     * @param entity
     * @return
     */
    @POST
    @Path("validate")
    @Consumes("application/json")
    @Produces("application/json")
    public FrEntityInvoice validate(Object entity) {
        FrEntityInvoice resultEntity = null;
        LOGGER.info(String.format("Input entity is [%s]", entity));
        resultEntity = new RestRegisterHelper<FrEntityInvoice>(registerClient, FrEntityInvoice.class){
            @Override
            public FrEntityInvoice run(FrEntityInvoice entity) {
                ((FrEntityInvoice)entity).getData().setTvaFr(10.5);
                Header header = ((FrEntityInvoice) entity).getData().getHeader();
                if(header == null){
                    header = new Header();
                }
                header.setClientId("10001");
                header.setSupplierId("FR121254");
                header.setScandate(new Date());

                LOGGER.info(String.format("Treating entity is [%s]", entity));
                return entity;
            }
        }.execute(entity);
        return resultEntity;
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
    public FrEntityInvoice stopAll(Object entity) {
        FrEntityInvoice resultEntity;
        resultEntity = new RestRegisterHelper<FrEntityInvoice>(registerClient, FrEntityInvoice.class){
            @Override
            public FrEntityInvoice run(FrEntityInvoice entity) {
                this.stopAll(entity);
                ((FrEntityInvoice)entity).getData().setTvaFr(10.5);
                return entity;
            }
        }.execute(entity);
        return resultEntity;
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
    public FrEntityInvoice stopChildren(Object entity) {
        FrEntityInvoice resultEntity;

        resultEntity = new RestRegisterHelper<FrEntityInvoice>(registerClient, FrEntityInvoice.class){
            @Override
            public FrEntityInvoice run(FrEntityInvoice entity) {
                this.stopChildren(entity);
                ((FrEntityInvoice)entity).getData().setTvaFr(10.5);
                return entity;
            }
        }.execute(entity);
        return resultEntity;
    }

}