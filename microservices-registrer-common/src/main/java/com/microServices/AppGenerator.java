package com.microservices;

import com.microservices.model.application.Application;
import com.microservices.model.application.EndPoint;

/**
 * Created by SZA on 01/03/2016.
 * Use for test or mock
 */
public class AppGenerator {
    public static Application build(int i) {
        Application app = new Application();
        app.setPath("/"+i);
        app.setId("app"+i);
        app.setHostName("127.0.0.1");
        app.setPort(8080+i);
        app.setInstanceId("1");
        app.setPriority(300.0+i);
        app.getEndPoints().add(new EndPoint("POST","path"));
        return app;
    }
}
