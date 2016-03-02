package com.microservices.facade;

import com.microservices.model.application.Application;
import com.microservices.model.Directory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by stephen on 27/02/2016.
 * Facade that provide a way to manage directory
 */
@Named
@Singleton
public class DirectoryFacade {

    @Inject
    Directory directory;

    /**
     * find registered app by id
     * @param appID application ID
     * @return Application
     */
    public Optional<Application> findRegisteredById(String appID) {
        Optional<Application> result;

        if(appID == null){
            throw new RuntimeException("AppId can't be null to find a Application in Directory");
        }

        result = directory.getRegisteredApps().stream().filter(app -> (
                app.getId().equals(appID))).findFirst();

        return result;
    }

    /**
     * Get all registered apps
     * @return Application
     */
    public List<Application> getAllRegistered() {
        return directory.getRegisteredApps();
    }

    /**
     * Register an app
     * @param app app to register
     */
    public void register(Application app) {
        if(app == null){
            throw new RuntimeException("Can't register a null Application");
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
     * @return Application app
     */
    public Optional<Application> findRegisteredByIdAndInstanceId(String appID, String instanceID) {
        Optional<Application> result;

        if(appID == null || instanceID == null ){
            throw new RuntimeException("AppId and instanceID can't be null to find a Application in Directory");
        }
        result = directory.getRegisteredApps().stream().
                filter(app -> (
                        app.getId().equals(appID) && app.getInstanceId().equals(instanceID))).findFirst();

        return result;
    }

    /**
     * Find children with parent app ID
     * sort by priority DESC
     * @param appID application ID
     * @return Application app
     */
    public List<Application> findRegisteredChildrenById(String appID) {
        List<Application> result;
        if(appID == null ){
            throw new RuntimeException("AppId can't be null to find a Application in Directory");
        }
        result = directory.getRegisteredApps().stream().
                filter(app -> (app.getParentApp().equals(appID))).collect(Collectors.toList());
        Collections.sort(result,(o1, o2) -> o1.getPriority().compareTo(o2.getPriority()));
        return result;
    }
}
