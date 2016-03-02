package com.microservices;

import com.microservices.model.App;
import com.microservices.model.EndPoint;

/**
 * Created by SZA on 01/03/2016.
 */
public class AppGenerator {
    public static App build(int i) {
        App app = new App();
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
