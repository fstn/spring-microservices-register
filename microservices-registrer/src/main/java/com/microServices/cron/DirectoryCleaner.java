package com.microServices.cron;

import com.microServices.model.Config;
import com.microServices.model.Directory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.stream.Collectors;

/**
 * Created by stephen on 28/02/2016.
 * Utils that clean directory input
 */
@Component
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
