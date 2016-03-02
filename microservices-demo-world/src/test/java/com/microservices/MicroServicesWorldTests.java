package com.microservices;

import com.microservices.model.*;
import com.microservices.utils.ChildrenWSUtilService;
import com.microservices.utils.RegisterUtilService;
import com.microservices.utils.RegisterWSUtilService;
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
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MicroServicesWorld.class)
public class MicroServicesWorldTests {
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
	private RegisterClient<EntityInvoice> registerClient;

	private List<App> childrenApps;
	private Invoice invoice;
	private Response childrenAppsMockResponse;
    private WebTarget webResource;
	private Invocation.Builder builder;
    private Response clientResponse;
    private Response invoiceResponse;
	private EntityInvoice invoiceEntity;

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
        childrenAppsMockResponse = mock(Response.class);
        webResource = mock( WebTarget.class );
		builder = mock(Invocation.Builder.class);
		when(builder.get()).thenReturn( clientResponse );

        clientResponse = mock( Response.class );
        when( clientResponse.getEntity()).thenReturn(childrenApps);
        when( clientResponse.getStatus() ).thenReturn( 200 );
		when(webResource.request()).thenReturn(builder);

        invoiceResponse = mock( Response.class );
        when( builder.get( Response.class ) ).thenReturn( invoiceResponse );
        when( invoiceResponse.getEntity()).thenReturn(invoiceEntity);
        when( invoiceResponse.getStatus() ).thenReturn( 200 );
		when(webResource.request()).thenReturn(builder);

        when(registerWSUtilService.callChildrenWS(childrenApps.getClass())).thenReturn( childrenApps);
        registerUtilService.registerWSUtilsService = registerWSUtilService;
        registerClient.registerUtil = registerUtilService;

        // mock execute client call
        when(registerUtilService.getChildren()).thenReturn(childrenApps);
        when(childrenWSUtilService.executeOnChildrenWS(any(),any(),any())).thenReturn(invoiceEntity);

	}
	@Test
	public void contextLoads() {
		Assert.assertEquals("world", app.getApp());
		Assert.assertEquals("127.0.0.1", app.getHostName());
		Assert.assertEquals((Integer) 8080, app.getPort());
		Assert.assertEquals("POST", app.getEndPoints().get(0).getMethod());
		Assert.assertEquals("127.0.0.1", register.getHostName());
		Assert.assertEquals((Integer) 8091, register.getPort());
	}

	@Test
	public void callClient(){
		//TODO
	}
}
