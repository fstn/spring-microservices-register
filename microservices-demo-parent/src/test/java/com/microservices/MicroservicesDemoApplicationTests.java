package com.microServices;

import com.microServices.model.App;
import com.microServices.model.Register;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MicroServicesDemoApplication.class)
public class MicroServicesDemoApplicationTests {


	@Inject
	private App app;

	@Inject
	private Register register;

	@Test
	public void contextLoads() {
		Assert.assertEquals("world", app.getApp());
		Assert.assertEquals("127.0.0.1", app.getHostName());
		Assert.assertEquals((Integer) 8080, app.getPort());
		Assert.assertEquals("POST", app.getEndPoints().get(0).getMethod());
		Assert.assertEquals("127.0.0.1", register.getHostName());
		Assert.assertEquals((Integer) 8092, register.getPort());
	}
}
