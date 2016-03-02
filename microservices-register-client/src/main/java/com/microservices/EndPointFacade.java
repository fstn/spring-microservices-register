package com.microservices;

import com.microservices.model.application.EndPoint;
import com.microservices.utils.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.ws.rs.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Named
public class EndPointFacade {

    private static final Logger logger = LoggerFactory.getLogger(EndPointFacade.class);
    /**
     * EndPoint cache to avoid to always use reflexion
     */
    private Map<Method,EndPoint> endPointCache = new HashMap<>();

    public EndPointFacade() {
    }

    /**
     * Return endPoint that match with method
     *
     * @param localClass
     * @return
     */
    public EndPoint createEndPoint(Class localClass) {
        logger.debug("EndPointFacade:createEndPoint");
        String errorMsg = "RestHelper must be use inside a JAX-RS endPoint,need have @Path, @POST,@GET,@PUT or @DELETE annotations";
        String method = null;
        Method m = localClass.getEnclosingMethod();
        EndPoint endPoint = endPointCache.get(m);
        if(endPoint == null) {
            Preconditions.checkNotNull(m, errorMsg);
            Path annotation = m.getAnnotationsByType(Path.class)[0];
            Preconditions.checkNotNull(annotation, errorMsg);

            String path = m.getAnnotationsByType(Path.class)[0].value();
            if (m.isAnnotationPresent(POST.class)) {
                method = HttpMethod.POST;
            } else if (m.isAnnotationPresent(GET.class)) {
                method = HttpMethod.GET;
            } else if (m.isAnnotationPresent(PUT.class)) {
                method = HttpMethod.PUT;
            } else if (m.isAnnotationPresent(DELETE.class)) {
                method = HttpMethod.DELETE;
            }
            endPoint = new EndPoint(path, method);
            endPointCache.put(m,endPoint);
        }
        return endPoint;
    }
}