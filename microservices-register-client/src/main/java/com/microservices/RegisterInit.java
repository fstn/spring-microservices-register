package com.microservices;

import com.microservices.model.App;
import com.microservices.model.Register;
import org.springframework.scheduling.annotation.Scheduled;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
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


			Client client = ClientBuilder.newBuilder().newClient();
			WebTarget target = client.target("http://"+register.getHostName()+":"
					+register.getPort());
			target = target.path("/apps/"+currentApp.getApp());

			Invocation.Builder builder = target.request();

			Response response = builder.get();

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
