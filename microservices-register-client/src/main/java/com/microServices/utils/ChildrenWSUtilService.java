package com.microservices.utils;

import com.microservices.model.application.Application;
import com.microservices.model.application.EndPoint;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by stephen on 27/02/2016.
 */
@Named
@Singleton
public class ChildrenWSUtilService<T> {

    private static final Logger logger = LoggerFactory.getLogger(ChildrenWSUtilService.class);
    /**
     * @param app
     * @param endPoint
     * @param result
     * @return
     */
    public T executeOnChildrenWS(Application app, EndPoint endPoint, T result) {

        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

        WebTarget target = client.target("http://" + app.getHostName() + ":"
                + app.getPort());
        target = target.path( "/" +app.getPath()+"/"+ endPoint.getPath());

        logger.debug("executeOnChildrenWS "+target.getUri().toString());

        Invocation.Builder builder = target.request();
        builder.accept(MediaType.APPLICATION_JSON);
        switch (endPoint.getMethod()) {
            case HttpMethod.GET:
                return builder.get((Class<T>) result.getClass().getClass());
            case HttpMethod.POST:
                return builder.post(Entity.json(result), (Class<T>) result.getClass());
            case HttpMethod.PUT:
                return builder.put(Entity.json(result), (Class<T>) result.getClass());
            case HttpMethod.DELETE:
                return builder.delete((Class<T>) result.getClass());
            default:
                throw new UnsupportedOperationException("Unable to execute method " + endPoint.getMethod());
        }
    }

}
