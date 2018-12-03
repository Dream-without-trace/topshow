package com.luwei.task;

import com.luwei.services.collect.CollectService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
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
@Slf4j
@DisallowConcurrentExecution
public class CollectJob implements Job {

    @Autowired
    private CollectService collectService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("CollectJob");
        collectService.deleteSoldOut();
    }


}
