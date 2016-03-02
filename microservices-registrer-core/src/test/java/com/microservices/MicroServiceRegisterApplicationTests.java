package com.microservices;

import com.microservices.cron.DirectoryCleaner;
import com.microservices.facade.DirectoryFacade;
import com.microservices.model.application.Application;
import com.microservices.model.Config;
import com.microservices.model.Directory;
import com.microservices.model.application.EndPoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;
import javax.ws.rs.HttpMethod;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by stephen on 28/02/2016.
 * Tests
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = MicroServiceRegisterApplication.class)
public class MicroServiceRegisterApplicationTests {

    @Inject
    Directory directory;

    @Inject
    DirectoryFacade directoryFacade;


    @Inject
    Application parentApp;

    @Mock
    Config config;

    @Inject
    @InjectMocks
    DirectoryCleaner directoryCleaner;
    private Application childApp;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // remove appTTL to test cleaning
        when(config.getAppTTL()).thenReturn(10);

        childApp = new Application();
        childApp.setPath("/eu/rest");
        childApp.setId("eu");
        childApp.setHostName("127.0.0.1");
        childApp.setPort(8080);
        childApp.setInstanceId("1");
        childApp.setPriority(200.0);
        ArrayList<EndPoint> endPoints = new ArrayList<>();
        endPoints.add(new EndPoint("validate", HttpMethod.POST));
        childApp.setEndPoints(endPoints);
        childApp.setParentApp(parentApp.getId());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void registerApp() throws InterruptedException {
        directory.setRegisteredApp(new ArrayList<>());
        directoryFacade.register(parentApp);
        List<Application> registerApps = directory.getRegisteredApps();
        assertEquals("There is one registered parentApp", 1, registerApps.size());
        assertEquals("My parentApp is registered", parentApp.getId(), registerApps.get(0).getId());
        Date oldLastUpdate = registerApps.get(0).getLastUpdate();

        //register second time the same parentApp
        Thread.sleep(100);
        directoryFacade.register(parentApp);
        registerApps = directory.getRegisteredApps();
        assertEquals("There is one registered parentApp", 1, registerApps.size());
        assertEquals("My parentApp is registered", parentApp.getId(), registerApps.get(0).getId());
        assertNotEquals("LastUpdated must have changed", oldLastUpdate, registerApps.get(0).getLastUpdate());
    }


    @Test
    public void findById() throws InterruptedException {
        directory.setRegisteredApp(new ArrayList<>());
        registerApp();
        Optional<Application> registeredApp = directoryFacade.findRegisteredById(parentApp.getId());
        assertTrue("Found a registered parentApp", registeredApp.isPresent());
        assertEquals("My parentApp is registered", parentApp.getId(), registeredApp.get().getId());

    }

    @Test
    public void appTTLToOld() throws InterruptedException {
        directory.setRegisteredApp(new ArrayList<>());
        registerApp();
        Thread.sleep(100);
        directoryCleaner.clean();
        List<Application> registerApps = directory.getRegisteredApps();
        assertEquals("Clean must remove my parentApp", 0, registerApps.size());
    }

    @Test
    public void appTTLOk() throws InterruptedException {
        directory.setRegisteredApp(new ArrayList<>());
        registerApp();
        directoryCleaner.clean();
        List<Application> registerApps = directory.getRegisteredApps();
        assertEquals("Clean must not remove my parentApp", 1, registerApps.size());
    }

    @Test
    public void appChildren() {
        directory.setRegisteredApp(new ArrayList<>());
        directoryFacade.register(parentApp);
        directoryFacade.register(childApp);
        List<Application> registeredChildren = directoryFacade.findRegisteredChildrenById(parentApp.getId());
        assertEquals("There is one registered childApp", 1, registeredChildren.size());
        assertEquals("My childApp is a child", childApp.getId(), registeredChildren.get(0).getId());
    }

    @Test
    public void appChildrenPrioritySort() {

        Application childApp2 = new Application();
        childApp2.setPath("/us/rest");
        childApp2.setId("usa");
        childApp2.setHostName("127.0.0.1");
        childApp2.setPort(8080);
        childApp2.setInstanceId("1");
        childApp2.setPriority(300.0);
        ArrayList<EndPoint> endPoints = new ArrayList<>();
        endPoints.add(new EndPoint("validate", HttpMethod.POST));
        childApp2.setEndPoints(endPoints);
        childApp2.setParentApp(parentApp.getId());
        directoryFacade.register(childApp2);
        List<Application> registeredChildren = directoryFacade.findRegisteredChildrenById(parentApp.getId());
        assertEquals("There is one registered childApp", 1, registeredChildren.size());
        assertEquals("My childApp is  childApp2 because priority is bigger", childApp2.getId(), registeredChildren.get(0).getId());
    }

}