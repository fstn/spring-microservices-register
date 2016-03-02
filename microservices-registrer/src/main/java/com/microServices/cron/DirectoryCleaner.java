package com.microservices.cron;

import com.microservices.model.Config;
import com.microservices.model.Directory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Calendar;
import java.util.stream.Collectors;

/**
 * Created by stephen on 28/02/2016.
 * Utils that clean directory input
 */
@Named
@Singleton
public class DirectoryCleaner {

    @Inject
    Directory directory;

    @Inject
    Config config;

    @Scheduled(fixedDelayString = "${config.cleaningDelay}")
    public void clean() {
        Calendar maxOlderAppCal = Calendar.getInstance();
        maxOlderAppCal.add(Calendar.MILLISECOND, -config.getAppTTL());
        //remove old apps from directory
        synchronized (directory.getRegisteredApps()) {
            directory.setRegisteredApp(
                    directory.getRegisteredApps().stream()
                            .filter(app -> (app.getLastUpdate().after(maxOlderAppCal.getTime())))
                            .collect(Collectors.toList()));
        }
    }
}
