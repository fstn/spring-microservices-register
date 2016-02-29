package com.microservices;

import com.microservices.model.App;
import com.microservices.model.Entity;
import com.microservices.model.Invoice;
import com.microservices.model.Register;
import com.microservices.utils.ChildrenWSUtilService;
import com.microservices.utils.RegisterUtilService;
import com.microservices.utils.RegisterWSUtilService;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MicroServicesDemoParentApplication.class)
public class MicroServicesDemoApplicationTests {
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
    private RegisterUtilService<Entity<Invoice>> registerUtilService;

	@Inject
	private RegisterClient<Entity<Invoice>> registerClient;

	private List<App> childrenApps;
	private Invoice invoice;
	private ClientResponse childrenAppsMockResponse;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private ClientResponse invoiceResponse;
	private Entity<Invoice> invoiceEntity;

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

		invoiceEntity = new Entity<>();
		invoiceEntity.setData(invoice);
        childrenAppsMockResponse = mock(ClientResponse.class);
        webResource = mock( WebResource.class );
        WebResource.Builder builder = mock( WebResource.Builder.class );

        clientResponse = mock( ClientResponse.class );
        when( builder.get( ClientResponse.class ) ).thenReturn( clientResponse );
        when( clientResponse.getEntity((GenericType<List<App>>) any()) ).thenReturn(childrenApps);
        when( clientResponse.getStatus() ).thenReturn( 200 );
        when( webResource.accept( anyString() ) ).thenReturn( builder );

        invoiceResponse = mock( ClientResponse.class );
        when( builder.get( ClientResponse.class ) ).thenReturn( invoiceResponse );
        when( invoiceResponse.getEntity((GenericType<Entity>) any()) ).thenReturn(invoiceEntity);
        when( invoiceResponse.getStatus() ).thenReturn( 200 );
        when( webResource.accept( anyString() ) ).thenReturn( builder );

        when(registerWSUtilService.callChildrenWS()).thenReturn(clientResponse );
        registerUtilService.registerWSUtilsService = registerWSUtilService;
        registerClient.registerUtil = registerUtilService;

        // mock execute client call
        when(childrenWSUtilService.executeOnChildrenWS(any(),any(),any())).thenReturn(invoiceResponse);

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
        registerClient.heartBeat();
		Entity<Invoice> entity = new Entity<>();
		entity.setData(invoice);
		Entity<Invoice> resultEntity  =  registerClient.executeOnChildren(app.getEndPoints().get(0),
				entity,
                new GenericType<Invoice>(){});
        //il y a 3 enfants à appele r
        verify(childrenWSUtilService.executeOnChildrenWS(any(),any(),any()),times(3));
		Assert.assertEquals(invoice.getAmount(),resultEntity.getData().getAmount());
	}
}
