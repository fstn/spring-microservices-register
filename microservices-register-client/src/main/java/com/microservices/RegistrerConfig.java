package com.microservices;

import com.microservices.model.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RegistrerConfig {
	List<App> childApis;
	@Autowired
	App apps;

	private static final Logger log = Logger.getLogger(RegistrerConfig.class
			.getName());

	@PostConstruct
	public void init() {
		log.log(Level.SEVERE, "init " + apps.toString());

		childApis = new ArrayList<App>();
		/*if (!apps.getParentName().equals("")) {

			RegisterInit registerInit = new RegisterInit(this);
			registerInit.start();
		}*/
	}

	@PreDestroy
	public void destroy() {
/*
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.postForObject(apps.getUrl() + apps.getName()
				+ "/rest/unRegisterChild", getApps(), String.class);*/

	}

	public void registerChildApi(App childApi) {
		childApis.add(childApi);
	}

	public void unRegisterChildApi(App childApi) {
		childApis.remove(childApi);
	}

	/**
	 * @return the childApis
	 */
	public List<App> getChildApis() {
		return childApis;
	}

	/**
	 * @param childApis
	 *            the childApis to set
	 */
	public void setChildApis(List<App> childApis) {
		this.childApis = childApis;
	}

	/**
	 * @return the apps
	 */
	public App getApps() {
		return apps;
	}

	/**
	 * @param apps
	 *            the apps to set
	 */
	public void setApps(App apps) {
		this.apps = apps;
	}

}
