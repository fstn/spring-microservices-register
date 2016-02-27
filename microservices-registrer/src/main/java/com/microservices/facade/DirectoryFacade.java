package com.microServices.facade;

import com.microServices.model.Directory;
import com.microServices.model.App;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by stephen on 27/02/2016.
 */
@Component
public class DirectoryFacade {

    @Inject
    Directory directory;

    /**
     * find registered app by id
     * @param appID
     * @return
     */
    public Optional<App> findRegisteredById(String appID) {
        Optional<App> result;

        if(appID == null){
            throw new RuntimeException("AppId can't be null to find a App in Directory");
        }

        //TODO modify for multi instance, not return first, we need to implement a loadbalancing way to find instance
        result = directory.getRegisteredApps().stream().filter(app -> (app.getApp().equals(appID))).findFirst();

        return result;
    }

    /**
     * Get all registered apps
     * @return
     */
    public List<App> getAllRegistered() {
        return directory.getRegisteredApps();
    }

    /**
     * Register an app
     * @param app
     */
    public void register(App app) {
        if(app == null){
            throw new RuntimeException("Can't register a null App");
        }
        directory.getRegisteredApps().add(app);
    }

    /**
     * Find registered app by using ID and instanceID
     * @param appID
     * @param instanceID
     * @return
     */
    public Optional<App> findRegisteredByIdAndInstanceId(String appID, String instanceID) {
        Optional<App> result;

        if(appID == null || instanceID == null ){
            throw new RuntimeException("AppId and instanceID can't be null to find a App in Directory");
        }
        result = directory.getRegisteredApps().stream().
                filter(app -> (app.getApp().equals(appID) && app.getInstanceID().equals(instanceID))).findFirst();

        return result;
    }

    /**
     * Find children with parent app ID
     * @param appID
     * @return
     */
    public List<App> findRegisteredChildrenById(String appID) {
        List<App> result;


        if(appID == null ){
            throw new RuntimeException("AppId can't be null to find a App in Directory");
        }
        result = directory.getRegisteredApps().stream().
                filter(app -> (app.getParentApp().equals(appID))).collect(Collectors.toList());

        return result;
    }
}
