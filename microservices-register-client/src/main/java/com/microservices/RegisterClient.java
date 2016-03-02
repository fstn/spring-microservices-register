package com.microservices;

import com.microservices.model.*;
import com.microservices.model.application.Application;
import com.microservices.model.application.EndPoint;
import com.microservices.model.stackTrace.StackTraceWSElement;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by stephen on 27/02/2016.
 * Client helper to use register service
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

    private Application currentApp;
    private Config config;
    /**
     * Children list
     */
    private List<Application> childrenApp;

    /**
     * Call on startup
     */
    @PostConstruct
    public void init() {
        logger.debug("RegisterClient:init");
        currentApp = appProducer.get();
        config = configProducer.get();
        //test configuration file
        configurationTester.checkApplicationConfiguration(currentApp);
        configurationTester.checkConfigConfiguration(config);
        registerUtil.register();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                childrenApp = registerUtil.getChildren();
            }
        }, 0, config.getReloadChildrenDelay());
    }

    /**
     * Never call with spring implementation
     */
    @PreDestroy
    public void destroy() {
        logger.debug("RegisterClient:destroy");
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
        logger.debug("RegisterClient:addStackCall");
        entity.getStackTrace().add(new StackTraceWSElement(currentApp, endPoint));
        return entity;
    }

    public List<Application> getChildrenApp() {
        return childrenApp;
    }

    public void setChildrenApp(List<Application> childrenApp) {
        this.childrenApp = childrenApp;
    }

    public RegisterUtilService<T> getRegisterUtil() {
        return registerUtil;
    }

    public void setRegisterUtil(RegisterUtilService<T> registerUtil) {
        this.registerUtil = registerUtil;
    }

    public Application getCurrentApp() {
        return currentApp;
    }

    public void setCurrentApp(Application currentApp) {
        this.currentApp = currentApp;
    }
}
