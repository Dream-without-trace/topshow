package com.luwei.controller;

import com.luwei.services.order.detail.OrderDetailService;
import com.luwei.services.order.detail.web.OrderDetailSimpleVO;
import com.luwei.services.order.detail.web.OrderDetailWebVO;
import com.luwei.services.order.web.AffirmOrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Leone
 * @since 2018-09-06
 **/
@Slf4j
@RestController
@Api(tags = "订单详情")
@RequestMapping("/api/order/detail")
public class OrderDetailController {

    @Resource
    private OrderDetailService orderDetailService;

    @GetMapping
    @ApiOperation("查找订单详情")
    public OrderDetailSimpleVO findOne(@RequestParam Integer orderDetailId) {
        return orderDetailService.findOne(orderDetailId);
    }

    @GetMapping("/unread")
    @ApiOperation("订单详情(未支付)")
    public AffirmOrderVO unreadOrderDetail(@RequestParam Integer orderId, @RequestParam Integer userId) {
        return orderDetailService.unreadOrderDetail(orderId, userId);
    }

//    @GetMapping("/paid")
//    @ApiOperation("订单详情(已支付)")
//    public OrderDetailWebVO paidOrderDetail(@RequestParam Integer orderId, @RequestParam Integer userId) {
//        return orderDetailService.paidOrderDetail(orderId, userId);
//    }


}
