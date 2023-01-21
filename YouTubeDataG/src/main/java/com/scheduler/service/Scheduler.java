package com.scheduler.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// Annotation
//@Component
// Class
public class Scheduler {

    // Method
    // To trigger the scheduler to run every two seconds
    @Scheduled(fixedRate = 2000)
    public void scheduleTask()
    {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        System.out.println(
                "Fixed rate Scheduler: Task running at - "
                        + strDate);
    }
    @Scheduled(fixedRate = 10000)
    public void scheduledTask1(){
        System.out.println("second scheduler also doing");
    }
}
