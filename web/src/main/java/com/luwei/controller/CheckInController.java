package com.luwei.controller;

import com.luwei.common.Response;
import com.luwei.services.checkIn.CheckInService;
import com.luwei.services.checkIn.web.CheckInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: topshow
 * @description: 签到Controller
 * @author: ZhangHongJie
 * @create: 2018-12-04 14:38
 **/
@Slf4j
@RestController
@Api(tags = "签到模块")
@RequestMapping("/api/checkIn")
public class CheckInController {

    @Resource
    private CheckInService checkInService;


    @PostMapping
    @ApiOperation("签到")
    public Response saveCheckIn(@RequestParam Integer userId){
        return Response.build(2000,"签到成功！",checkInService.saveCheckIn(userId,1));
    }


    @GetMapping
    @ApiOperation("签到详情")
    public CheckInVo getCheckInVo(@RequestParam Integer userId){
        return checkInService.getCheckInVo(userId);
    }





}
