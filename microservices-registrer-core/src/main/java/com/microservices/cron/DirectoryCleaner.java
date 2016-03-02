package com.microservices.cron;

import com.microservices.model.Directory;
import com.microservices.producer.ConfigProducer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
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
    ConfigProducer configProducer;

    @PostConstruct
    public void init(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                clean();
            }
        }, 0, configProducer.get().getCleaningDelay());
    }

    //@Scheduled(fixedDelayString = "${config.cleaningDelay}")
    public void clean() {
        Calendar maxOlderAppCal = Calendar.getInstance();
        maxOlderAppCal.add(Calendar.MILLISECOND, -configProducer.get().getAppTTL());
        //remove old apps from directory
        synchronized (directory.getRegisteredApps()) {
            directory.setRegisteredApp(
                    directory.getRegisteredApps().stream()
                            .filter(app -> (app.getLastUpdate().after(maxOlderAppCal.getTime())))
                            .collect(Collectors.toList()));
        }
    }
}
