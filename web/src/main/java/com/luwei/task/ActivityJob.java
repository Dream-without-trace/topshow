package com.luwei.task;

import com.luwei.services.activity.ActivityService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-15
 **/
public class ActivityJob implements Job {

    @Autowired
    private ActivityService activityService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        activityService.updateOverActivityStatus();
        activityService.updateUnderwayActivityStatus();
    }

}
