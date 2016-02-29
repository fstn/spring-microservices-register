package com.microservices.utils;

import com.microservices.model.App;
import com.microservices.model.EndPoint;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;

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
    public Response executeOnChildrenWS(App app, EndPoint endPoint, T result) {

        Client client = ClientBuilder.newBuilder().newClient();
        WebTarget target = client.target("http://" + app.getHostName() + ":"
                + app.getPort());
        target = target.path( "/" +app.getPath()+"/"+ endPoint.getPath());

        Invocation.Builder builder = target.request();

        switch (endPoint.getMethod()) {
            case HttpMethod.GET:
                return builder.get();
            case HttpMethod.POST:
                return builder.post(Entity.json(result));
            case HttpMethod.PUT:
                return builder.put(Entity.json(result));
            case HttpMethod.DELETE:
                return builder.delete();
            default:
                throw new UnsupportedOperationException("Unable to execute method " + endPoint.getMethod());
        }
    }

}
