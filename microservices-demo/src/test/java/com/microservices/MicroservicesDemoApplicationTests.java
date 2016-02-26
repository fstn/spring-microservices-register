package com.microservices;

import com.microservices.model.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MicroservicesDemoApplication.class)
public class MicroservicesDemoApplicationTests {


	@Autowired
	private App app;

	@Test
	public void contextLoads() {
		System.out.println(app);
	}

}
