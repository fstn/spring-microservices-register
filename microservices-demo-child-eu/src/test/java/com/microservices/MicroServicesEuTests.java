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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MicroServicesEu.class)
public class MicroServicesEuTests {
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
        childrenApps.add(app);
        childrenApps.add(app);
        childrenApps.add(app);
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

        when(registerUtilService.getChildren()).thenReturn(new AppListDTO(childrenApps));
        when(childrenWSUtilService.executeOnChildrenWS(any(), any(), any())).thenReturn(invoiceEntity);
    }

    @Test
    public void contextLoads() {
        Assert.assertEquals("eu", app.getApp());
        Assert.assertEquals("127.0.0.1", app.getHostName());
        Assert.assertEquals((Integer) 8093, app.getPort());
        Assert.assertEquals("POST", app.getEndPoints().get(0).getMethod());
        Assert.assertEquals("127.0.0.1", register.getHostName());
        Assert.assertEquals((Integer) 8091, register.getPort());
    }

    @Test
    public void callClient() {
      //TODO TU sur le helper
    }
}
