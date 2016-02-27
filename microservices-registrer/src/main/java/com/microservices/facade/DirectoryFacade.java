package com.microServices.facade;

import com.microServices.model.Directory;
import com.microServices.model.App;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by stephen on 27/02/2016.
 */
@Component
public class DirectoryFacade {

    @Inject
    Directory directory;

    public Optional<App> findRegisteredById(String appID) {
        Optional<App> result;

        if(appID == null){
            throw new RuntimeException("AppId wan't be null to find a App in Directory");
        }

        //TODO modify for multi instance, not return first, we need to implement a loadbalancing way to find instance
        result = directory.getRegisteredApps().stream().filter(app -> (app.getApp().equals(appID))).findFirst();

        return result;
    }

    public List<App> getAllRegistered() {
        return directory.getRegisteredApps();
    }

    public void register(App app) {
        if(app == null){
            throw new RuntimeException("Can't register a null App");
        }
        directory.getRegisteredApps().add(app);
    }
}
