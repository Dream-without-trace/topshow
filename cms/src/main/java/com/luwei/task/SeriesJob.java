package com.luwei.task;

import com.luwei.models.activity.series.ActivitySeries;
import com.luwei.services.activity.ActivityService;
import com.luwei.services.activity.series.ActivitySeriesService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 *
 * @author Leone
 * @since Wednesday September
 **/
@Slf4j
@Component
@EnableScheduling
public class SeriesJob implements Job {

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivitySeriesService activitySeriesService;

    /**
     * 同步系列数量
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<ActivitySeries> result = activitySeriesService.findAll();
        log.info("task execute success list size:{}", result.size());
        result.forEach(e -> e.setCount(activityService.countSeries(e.getActivitySeriesId())));
        activitySeriesService.saveAll(result);
    }
}