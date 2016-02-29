package com.microservices;

import com.microservices.model.App;
import com.microservices.model.EndPoint;
import com.microservices.model.Entity;
import com.microservices.model.StackTraceWSElement;
import com.microservices.utils.RegisterUtilService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by stephen on 27/02/2016.
 */
@Component
@Singleton
@EnableScheduling
public class RegisterClient<T> {

    @Inject
    RegisterUtilService<T> registerUtil;

    @Inject
    App app;
    /**
     * Children list
     */
    private List<App> childrenApp;

    @PostConstruct
    public void init() {
        registerUtil.register();
        heartBeat();
    }

    @PreDestroy
    public void destroy() {
        registerUtil.unregister();
    }

    @Scheduled(fixedDelayString  = "${config.heartBeatDelay}")
    public void heartBeat() {
        // get childs
        childrenApp = registerUtil.getChildren();
    }

    /**
     * Execute method on children
     * @param endPoint
     * @param param
     * @return
     */
    public T executeOnChildren(EndPoint endPoint, T param){
        // call childs
        T result = registerUtil.executeOnChildren(childrenApp,endPoint,param);
        //return child result
        return result;
    }

    /**
     * Add StackCall inside entity
     * @param entity
     * @param endPoint
     * @return
     */
    public Entity addStackCall(Entity entity, EndPoint endPoint) {
        entity.getStackTrace().add(new StackTraceWSElement(app,endPoint));
        return entity;
    }

    /**
     * Execute method on children
     * @param entity
     * @param localClass
     * @return
     */
    public T executeOnChildren(T entity, Class localClass) {
        if(! (entity instanceof  Entity)){
            throw new RuntimeException("entity must be an Entity");
        }
        Method m = localClass.getEnclosingMethod();
        String path = ((Path)m.getAnnotationsByType(Path.class)[0]).value();
        String method = null;


        if(m.isAnnotationPresent(POST.class)) {
            method = HttpMethod.POST;
        }else if(m.isAnnotationPresent(GET.class)) {
            method = HttpMethod.GET;
        }else if(m.isAnnotationPresent(PUT.class)) {
            method = HttpMethod.PUT;
        }else if(m.isAnnotationPresent(DELETE.class)) {
            method = HttpMethod.DELETE;
        }
        EndPoint endPoint = new EndPoint(path, method) ;
        T result = registerUtil.executeOnChildren(childrenApp,endPoint,entity);
        ((Entity)result).getStackTrace().add(new StackTraceWSElement(app,endPoint));
        return result;
    }

}
