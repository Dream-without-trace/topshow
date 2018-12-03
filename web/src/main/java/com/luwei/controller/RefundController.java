package com.luwei.controller;

import com.luwei.services.refund.RefundService;
import com.luwei.services.refund.web.RefundAddDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Leone
 * @since 2018-08-23
 **/
@Slf4j
@RestController
@Api(tags = "退款模块")
@RequestMapping("/api/refund")
public class RefundController {

    @Resource
    private RefundService refundService;

    @PostMapping
    @ApiOperation("申请退款")
    public Object refund(@RequestBody RefundAddDTO dto) {
        return refundService.applyRefund(dto);
    }


}
