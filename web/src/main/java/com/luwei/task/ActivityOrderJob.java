package com.luwei.task;

import com.luwei.common.enums.status.ActivityOrderStatus;
import com.luwei.models.activity.order.ActivityOrder;
import com.luwei.services.activity.order.ActivityOrderService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


/**
 * <p>
 *
 * @author Leone
 * @since Friday September
 **/
@Slf4j
@DisallowConcurrentExecution
public class ActivityOrderJob implements Job {

    @Autowired
    private ActivityOrderService activityOrderService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<ActivityOrder> result = activityOrderService.findOverdue(new Date(), ActivityOrderStatus.PAY);
        log.info("task execute success list size:{}", result.size());
        result.forEach(e -> e.setStatus(ActivityOrderStatus.JOIN));
        activityOrderService.updateActivityOrderStatus(result);
    }
}