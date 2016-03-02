package com.microservices.producer;

import com.microservices.model.App;
import com.microservices.model.EndPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.ws.rs.HttpMethod;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by SZA on 02/03/2016.
 */
@Named
public class AppProducer {

    private static final Logger logger = LoggerFactory.getLogger(AppProducer.class);

    private App app;

    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        try {
            ClassLoader loader = this.getClass().getClassLoader();
            System.out.println(loader.getResource("application.properties"));
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("application.properties");
            if (is != null) {
                properties.load(is);
                app = new App();
                String propertieValue = properties.getProperty("app.id");
                if (propertieValue != null)
                    app.setId(propertieValue);

                propertieValue = properties.getProperty("app.instanceId");
                if (propertieValue != null)
                    app.setInstanceId(propertieValue);

                propertieValue = properties.getProperty("app.parentApp");
                if (propertieValue != null)
                    app.setParentApp(propertieValue);

                propertieValue = properties.getProperty("app.priority");
                if (propertieValue != null)
                    app.setPriority(Double.parseDouble(propertieValue));

                propertieValue = properties.getProperty("app.hostName");
                if (propertieValue != null)
                    app.setHostName(propertieValue);
                propertieValue = properties.getProperty("app.port");
                if (propertieValue != null)
                    app.setPort(Integer.parseInt(propertieValue));

                propertieValue = properties.getProperty("app.path");
                if (propertieValue != null)
                    app.setPath(propertieValue);

                ArrayList<EndPoint> endPointsList = new ArrayList<>();
                String postEndPoints = properties.getProperty("app.endPoints.post");
                if (postEndPoints != null && !postEndPoints.equals("")) {
                    String[] postEndPointsArray = postEndPoints.split(",");
                    for (int i = 0; i < postEndPointsArray.length; i++) {
                        endPointsList.add(new EndPoint( postEndPointsArray[i],HttpMethod.POST));
                    }
                }
                String getEndPoints = properties.getProperty("app.endPoints.get");
                if (getEndPoints != null && !getEndPoints.equals("")) {
                    String[] getEndPointsArray = getEndPoints.split(",");
                    for (int i = 0; i < getEndPointsArray.length; i++) {
                        endPointsList.add(new EndPoint(getEndPointsArray[i],HttpMethod.GET));
                    }
                }
                String putEndPoints = properties.getProperty("app.endPoints.put");
                if (putEndPoints != null && !putEndPoints.equals("")) {
                    String[] putEndPointsArray = putEndPoints.split(",");
                    for (int i = 0; i < putEndPointsArray.length; i++) {
                        endPointsList.add(new EndPoint(putEndPointsArray[i],HttpMethod.PUT));
                    }
                }
                String deleteEndPoints = properties.getProperty("app.endPoints.delete");
                if (deleteEndPoints != null && !deleteEndPoints.equals("")) {
                    String[] deleteEndPointsArray = deleteEndPoints.split(",");
                    for (int i = 0; i < deleteEndPointsArray.length; i++) {
                        endPointsList.add(new EndPoint(deleteEndPointsArray[i],HttpMethod.DELETE));
                    }
                }

                app.setEndPoints(endPointsList);
            }

        } catch (IOException e) {
            logger.error("Unable to load application.properties", e);
        }
    }


    @Produces
    public App get() {
        return app;
    }
}
