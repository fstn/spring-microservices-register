package com.microServices;

import com.microServices.utils.RegisterUtilService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by stephen on 27/02/2016.
 */
@Component
@Singleton
public class RegisterClient {

    @Inject
    RegisterUtilService registerUtil;

    @PostConstruct
    private void init() {
        registerUtil.register();
    }

    @PreDestroy
    private void destroy() {
        registerUtil.unregister();
    }
}
