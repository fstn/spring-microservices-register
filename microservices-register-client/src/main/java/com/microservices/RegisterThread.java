package com.microservices;

import com.microservices.model.application.Application;
import com.microservices.model.Register;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;

/**
 * Created by stephen on 27/02/2016.
 * Thread use to register on register service,
 * try 100 time before cancel registration
 */
public class RegisterThread extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(RegisterThread.class);
	private Application currentApp;
	private Register register;
	private boolean registered = false;

	public RegisterThread(Application currentApp, Register register) {
		super();
		this.currentApp = currentApp;
		this.register = register;
	}

	public void run() {
		logger.debug("RegisterThread:init");
		int currentTry = 0;
		while (!registered && currentTry < 100) {
			currentTry++;
			Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
			WebTarget target = client.target("http://"+register.getHostName()+":"
					+register.getPort());
			target = target.path("/apps/"+currentApp.getId());

			Invocation.Builder builder = target.request();

			try {
				Response response = builder.post(Entity.json(currentApp));
				if( response.getStatus() > 300){
					throw new Exception("Bad response status "+response.getStatus());
				}
			}catch (Exception e){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException ex) {
					logger.error( "error " + currentApp.getId(),ex);
				}
			}
		}
	}
}
