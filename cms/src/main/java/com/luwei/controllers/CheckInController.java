package com.luwei.controllers;

import com.luwei.common.Response;
import com.luwei.services.checkIn.CheckInService;
import com.luwei.services.user.cms.UserInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: topshow
 * @description: 签到
 * @author: ZhangHongJie
 * @create: 2018-12-06 10:32
 **/
@Slf4j
@RestController
@Api(tags = "线下签到模块")
@RequestMapping("api/checkIn")
public class CheckInController {

    @Resource
    private CheckInService checkInService;

    @GetMapping
    @ApiOperation("根据手机号查询用户信息")
    public UserInfoVo getUserInfoVoByPhone(@RequestParam @ApiParam("用户手机号")String phone){
        return checkInService.getUserInfoVoByPhone(phone);
    }

    @PostMapping
    @ApiOperation("线下签到")
    public Response offlineCheckIn(@RequestParam @ApiParam("用户Id")Integer userId){
        return Response.build(2000,"签到成功！",checkInService.saveCheckIn(userId,2));
    }



}
