package com.microservices;

import com.microservices.model.*;
import com.microservices.producer.AppProducer;
import com.microservices.producer.ConfigProducer;
import com.microservices.utils.RegisterUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by stephen on 27/02/2016.
 */
@Named
@Singleton
public class RegisterClient<T> {

    private static final Logger logger = LoggerFactory.getLogger(RegisterClient.class);
    @Inject
    com.microservices.ConfigurationTester configurationTester;
    @Inject
    RegisterUtilService<T> registerUtil;
    @Inject
    ConfigProducer configProducer;
    @Inject
    AppProducer appProducer;


    private App app;
    private Config config;
    /**
     * Children list
     */
    private List<App> childrenApp;

    @PostConstruct
    public void init() {
        app = appProducer.get();
        config = configProducer.get();
        //test configuration file
        configurationTester.testAppConfiguration(app);
        configurationTester.testConfigConfiguration(config);
        registerUtil.register();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                childrenApp = registerUtil.getChildren();
            }
        }, 0, config.getReloadChildrenDelay());
    }

    @PreDestroy
    public void destroy() {
        registerUtil.unregister();
    }

    /**
     * Add StackCall inside entity
     *
     * @param entity
     * @param endPoint
     * @return
     */
    public Entity addStackCall(Entity entity, EndPoint endPoint) {
        entity.getStackTrace().add(new StackTraceWSElement(app, endPoint));
        return entity;
    }

    /**
     * Return endPoint that match with method
     *
     * @param localClass
     * @return
     */
    public EndPoint getEndPoint(Class localClass) {
        Method m = localClass.getEnclosingMethod();
        String path = m.getAnnotationsByType(Path.class)[0].value();
        String method = null;
        if (m.isAnnotationPresent(POST.class)) {
            method = HttpMethod.POST;
        } else if (m.isAnnotationPresent(GET.class)) {
            method = HttpMethod.GET;
        } else if (m.isAnnotationPresent(PUT.class)) {
            method = HttpMethod.PUT;
        } else if (m.isAnnotationPresent(DELETE.class)) {
            method = HttpMethod.DELETE;
        }
        EndPoint endPoint = new EndPoint(path, method);
        return endPoint;
    }


    public List<App> getChildrenApp() {
        return childrenApp;
    }

    public void setChildrenApp(List<App> childrenApp) {
        this.childrenApp = childrenApp;
    }

    public RegisterUtilService<T> getRegisterUtil() {
        return registerUtil;
    }

    public void setRegisterUtil(RegisterUtilService<T> registerUtil) {
        this.registerUtil = registerUtil;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
