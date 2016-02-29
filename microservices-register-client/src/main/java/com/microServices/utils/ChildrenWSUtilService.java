package com.microservices.utils;

import com.microservices.model.App;
import com.microservices.model.EndPoint;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;

/**
 * Created by stephen on 27/02/2016.
 */
@Component
@Singleton
public class ChildrenWSUtilService<T> {
    /**
     * @param app
     * @param endPoint
     * @param result
     * @return
     */
    public ClientResponse executeOnChildrenWS(App app, EndPoint endPoint, T result) {
        Client client = Client.create();

        WebResource webResource = client
                .resource("http://" + app.getHostName() + ":"
                        + app.getPort() + "/" +app.getPath()+"/"+ endPoint.getPath());

        switch (endPoint.getMethod()) {
            case HttpMethod.GET:
                return webResource
                        .get(ClientResponse.class);
            case HttpMethod.POST:
                return webResource.type(MediaType.APPLICATION_JSON)
                        .post(ClientResponse.class, result);
            case HttpMethod.PUT:
                return webResource.type(MediaType.APPLICATION_JSON)
                        .put(ClientResponse.class, result);
            case HttpMethod.DELETE:
                return webResource.type(MediaType.APPLICATION_JSON)
                        .delete(ClientResponse.class, result);
            default:
                throw new UnsupportedOperationException("Unable to execute method " + endPoint.getMethod());
        }
    }

}
