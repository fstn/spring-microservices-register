package com.microservices.facade;

import com.microservices.model.App;
import com.microservices.model.Directory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by stephen on 27/02/2016.
 * Facade that provide a way to manage directory
 */
@Component
public class DirectoryFacade {

    @Inject
    Directory directory;

    /**
     * find registered app by id
     * @param appID application ID
     * @return App
     */
    public Optional<App> findRegisteredById(String appID) {
        Optional<App> result;

        if(appID == null){
            throw new RuntimeException("AppId can't be null to find a App in Directory");
        }

        result = directory.getRegisteredApps().stream().filter(app -> (
                app.getApp().equals(appID))).findFirst();

        return result;
    }

    /**
     * Get all registered apps
     * @return App
     */
    public List<App> getAllRegistered() {
        return directory.getRegisteredApps();
    }

    /**
     * Register an app
     * @param app app to register
     */
    public void register(App app) {
        if(app == null){
            throw new RuntimeException("Can't register a null App");
        }
        // remove existing app
        if( directory.getRegisteredApps().contains(app)){
            directory.getRegisteredApps().remove(app);
        }
        // update heartBeat
        app.setLastUpdate(new Date());
        directory.getRegisteredApps().add(app);

    }

    /**
     * Find registered app by using ID and instanceID
     * @param appID application ID
     * @param instanceID instance ID
     * @return App app
     */
    public Optional<App> findRegisteredByIdAndInstanceId(String appID, String instanceID) {
        Optional<App> result;

        if(appID == null || instanceID == null ){
            throw new RuntimeException("AppId and instanceID can't be null to find a App in Directory");
        }
        result = directory.getRegisteredApps().stream().
                filter(app -> (
                        app.getApp().equals(appID) && app.getInstanceID().equals(instanceID))).findFirst();

        return result;
    }

    /**
     * Find children with parent app ID
     * sort by priority DESC
     * @param appID application ID
     * @return App app
     */
    public List<App> findRegisteredChildrenById(String appID) {
        List<App> result;
        if(appID == null ){
            throw new RuntimeException("AppId can't be null to find a App in Directory");
        }
        result = directory.getRegisteredApps().stream().
                filter(app -> (app.getParentApp().equals(appID))).collect(Collectors.toList());
        Collections.sort(result,(o1, o2) -> o1.getPriority().compareTo(o2.getPriority()));
        return result;
    }
}
