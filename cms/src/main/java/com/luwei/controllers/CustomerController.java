package com.luwei.controllers;

import com.luwei.common.Response;
import com.luwei.services.customer.CustomerService;
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
 * @since Friday September
 **/
@Slf4j
@RestController
@Api(tags = "客服模块")
@RequestMapping("api/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @GetMapping("/check")
    @ApiOperation("检查客服是否存在")
    public Response check(@RequestParam String customerId) {
        return Response.success(customerService.check(customerId));
    }

}
