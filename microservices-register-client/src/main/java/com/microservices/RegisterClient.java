package com.microServices;

import com.microServices.model.App;
import com.microServices.model.EndPoint;
import com.microServices.model.Entity;
import com.microServices.model.StackTraceWSElement;
import com.microServices.utils.RegisterUtilService;
import com.sun.jersey.api.client.GenericType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * Created by stephen on 27/02/2016.
 */
@Component
@Singleton
@EnableScheduling
public class RegisterClient {

    @Inject
    RegisterUtilService registerUtil;

    @Inject
    App app;
    /**
     * Children list
     */
    private List<App> childrenApp;

    @PostConstruct
    public void init() {
        registerUtil.register();
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
     * @param type entity type @example GenericType<Invoice>
     * @return
     */
    public Object executeOnChildren(EndPoint endPoint, Object param, GenericType type){
        // call childs
        Object result = registerUtil.executeOnChildren(childrenApp,endPoint,param,type);
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
}
