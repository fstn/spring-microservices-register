package com.microservices;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.logging.Logger;

public class RegisterInit extends Thread {

	private RegistrerConfig register;
	private boolean registered = false;
	private static final Logger log = Logger.getLogger(RegistrerConfig.class
			.getName());

	public RegisterInit(RegistrerConfig register) {
		super();
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
					.resource("http://localhost:8080/RESTfulExample/rest/json/metallica/get");

			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {

			}

/*
			RestTemplate restTemplate = new RestTemplate();
			String context = restTemplate.postForObject(register.getApps().getUrl() + "/"
					+ register.getApps().getParentName() + "/"
					+ register.getApps().getParentRegistrerAction(),
					register.getApps(), String.class);
			if (! context.equals("ok")) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					log.log(Level.SEVERE, "error " + register.getApps());
				}
			} else {
				registered = true;
			}*/
		}

		if (!registered) {
			throw new RuntimeException("Unable to find parent API "
					+ register.getApps());
		}
	}
}
