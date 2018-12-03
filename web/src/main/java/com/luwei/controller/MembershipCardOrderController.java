package com.luwei.controller;

import com.luwei.common.Response;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.services.membershipCard.order.MembershipCardOrderService;
import com.luwei.services.membershipCard.order.web.MembershipCardAddOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @program: topshow
 * @description: 办理会员卡订单Controller
 * @author: ZhangHongJie
 * @create: 2018-12-03 19:59
 **/
@Slf4j
@RestController
@Api(tags = "会员卡订单模块")
@RequestMapping("/api/membershipCard/order")
public class MembershipCardOrderController {

    @Resource
    private MembershipCardOrderService membershipCardOrderService;


    @PostMapping
    @ApiOperation("添加")
    public Response save(@RequestBody @Valid MembershipCardAddOrder dto, HttpServletRequest request) {
        Integer userId = UserHelper.getId();
        return Response.build(20000, "success", membershipCardOrderService.save(dto,request));
    }





}
