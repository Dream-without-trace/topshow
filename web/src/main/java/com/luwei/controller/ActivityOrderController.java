package com.luwei.controller;

import com.luwei.common.enums.status.ActivityOrderStatus;
import com.luwei.services.activity.order.ActivityOrderService;
import com.luwei.services.activity.order.web.ActivityOrderWebPageVO;
import com.luwei.services.activity.web.ActivityAffirmVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Slf4j
@RestController
@Api(tags = "活动订单模块")
@RequestMapping("/api/activity/order")
public class ActivityOrderController {

    @Resource
    private ActivityOrderService activityOrderService;

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Page<ActivityOrderWebPageVO> page(@PageableDefault Pageable pageable,
                                             @RequestParam Integer userId,
                                             @RequestParam(required = false) ActivityOrderStatus status) {
        return activityOrderService.page(pageable, userId, status);
    }

    @DeleteMapping
    @ApiOperation("删除活动订单")
    public void delete(@RequestParam Integer userId, @RequestParam Integer activityOrderId) {
        activityOrderService.delete(userId, activityOrderId);
    }

    @GetMapping("/one")
    @ApiOperation("获取一个")
    public ActivityOrderWebPageVO findOne(@RequestParam Integer userId, @RequestParam Integer activityOrderId) {
        return activityOrderService.findOne(userId, activityOrderId);
    }


    @GetMapping("/detail")
    @ApiOperation("获取活动订单详情")
    public ActivityAffirmVO activityOrderDetail(@RequestParam Integer orderId) {
        return activityOrderService.findOrderDetail(orderId);
    }

}
