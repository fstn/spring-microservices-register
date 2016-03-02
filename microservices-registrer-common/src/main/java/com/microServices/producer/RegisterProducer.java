package com.microservices.producer;

import com.microservices.model.Register;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by SZA on 02/03/2016.
 */
@Named
public class RegisterProducer {

    private Register register;

    @PostConstruct
    public void init() throws IOException {
        Properties properties = new Properties();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        if (is != null) {
            properties.load(is);
            register = new Register();

            String hostName = properties.getProperty("register.hostName");
            if (hostName != null)
                register.setHostName(hostName);

            String port = properties.getProperty("register.port");
            if (port != null)
                register.setPort(Integer.parseInt(port));
        }
    }

    @Produces
    public Register get(){
        return register;
    }
}
