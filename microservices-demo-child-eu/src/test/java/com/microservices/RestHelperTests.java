package com.microservices;

import com.microservices.model.*;
import com.microservices.utils.ChildrenWSUtilService;
import com.microservices.utils.RegisterUtilService;
import com.microservices.utils.RegisterWSUtilService;
import org.apache.catalina.WebResource;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MicroServicesEu.class)
public class RestHelperTests {
    @Mock
    private RegisterWSUtilService registerWSUtilService;

    @Mock
    private ChildrenWSUtilService childrenWSUtilService;
    // Testing instance, mocked `resource` should be injected here

    @Inject
    private App app;

    @Inject
    private Register register;

    @Inject
    @InjectMocks
    private RegisterUtilService<EntityInvoice> registerUtilService;

    @Inject
    @InjectMocks
    private RegisterClient<EntityInvoice> registerClient;

    private List<App> childrenApps;
    private Invoice invoice;
    private EntityInvoice invoiceEntity;
    private ClientResponse childrenAppsMockResponse;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private ClientResponse invoiceResponse;

    @Before
    public void startUp() {

        MockitoAnnotations.initMocks(this);
        childrenApps = new ArrayList<>();
        App app2 = AppGenerator.build(2);
        App app3 = AppGenerator.build(3);
        childrenApps.add(app2);
        childrenApps.add(app3);
        invoice = new Invoice();
        invoice.setAmount(10.50);
        invoice.setId("1212");
        invoice.setTva(19.6);

        invoiceEntity = new EntityInvoice();
        invoiceEntity.setData(invoice);
        childrenAppsMockResponse = mock(ClientResponse.class);
        webResource = mock(WebResource.class);

        when(registerWSUtilService.callChildrenWS(childrenApps.getClass())).thenReturn(childrenApps);
        registerUtilService.registerWSUtilsService = registerWSUtilService;
        registerClient.registerUtil = registerUtilService;

        // mock execute client call

        when(registerUtilService.getChildren()).thenReturn(childrenApps);
        when(childrenWSUtilService.executeOnChildrenWS(any(), any(), any())).
                thenReturn(registerClient.addStackCall(invoiceEntity,new EndPoint()));

    }

    /**
     * Test stop flag inside service
     */
    @Test
    @POST
    @Path("validate")
    @Consumes("application/json")
    @Produces("application/json")
    public void stopInvoice() {
        registerClient.setChildrenApp(childrenApps);
        EntityInvoice testInvoice = invoiceEntity;
        testInvoice = new RestRegisterHelper<EntityInvoice>(registerClient, EntityInvoice.class) {
            @Override
            public EntityInvoice run(EntityInvoice entity) {
                ((EntityInvoice) entity).getData().setTvaIntraCom("10.5");
                return entity;
            }
        }.execute(testInvoice);


        Assert.assertEquals(false, testInvoice.isStopChildren());
        Assert.assertEquals("Invoice entity don't have stop flag, it must be executed on child endpoint",2, testInvoice.getStackTrace().size());

        testInvoice.setStopChildren(true);

        Assert.assertEquals(true, testInvoice.isStopChildren());
        testInvoice.setStackTrace(new ArrayList<>());
        testInvoice = new RestRegisterHelper<EntityInvoice>(registerClient, EntityInvoice.class) {
            @Override
            public EntityInvoice run(EntityInvoice entity) {
                ((EntityInvoice) entity).getData().setTvaIntraCom("10.5");
                return entity;
            }
        }.execute(testInvoice);
        Assert.assertEquals(false, testInvoice.isStopChildren());

        Assert.assertEquals("Invoice entity have stop flag, it must not be executed on child endpoint",1, testInvoice.getStackTrace().size());

        invoiceEntity.setStopChildren(false);

    }

}
