package com.microservices;

import com.microservices.model.App;
import com.microservices.model.Register;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.scheduling.annotation.Scheduled;

import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterInit extends Thread {

	private App currentApp;
	private Register register;
	private boolean registered = false;
	private static final Logger log = Logger.getLogger(RegistrerConfig.class
			.getName());

	public RegisterInit(App currentApp,Register register) {
		super();
		this.currentApp = currentApp;
		this.register = register;
	}

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {
        System.out.println("The time is now " );
    }

	public void run() {
		int currentTry = 0;
		while (!registered && currentTry < 100) {
			currentTry++;
			Client client = Client.create();


			WebResource webResource = client
					.resource("http://"+register.getHostName()+":"
							+register.getPort()+"/apps/"+currentApp.getApp());

			ClientResponse response = webResource
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class,  currentApp);

			if (response.getStatus() == 200 || response.getStatus() == 204) {
				registered = true;
			}else{
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					log.log(Level.SEVERE, "error " + currentApp.getApp());
				}
			}
		}

		if (!registered) {
			throw new RuntimeException("Unable to find parent API "
					+ currentApp.getApp());
		}
	}
}
