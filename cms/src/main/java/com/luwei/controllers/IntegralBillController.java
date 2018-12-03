package com.luwei.controllers;

import com.luwei.services.integral.bill.IntegralBillService;
import com.luwei.services.integral.bill.cms.IntegralBillPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Slf4j
@RestController
@Api(tags = "积分账单模块")
@RequestMapping("api/integral/bill")
public class IntegralBillController {

    @Resource
    private IntegralBillService integralBillService;

    @GetMapping("/page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", paramType = "query", type = "String"),
            @ApiImplicitParam(name = "end", paramType = "query", type = "String")})
    public Page<IntegralBillPageVO> page(@RequestParam(required = false) String phone,
                                         @RequestParam(required = false) Date start,
                                         @RequestParam(required = false) Date end,
                                         @PageableDefault Pageable pageable) {
        return integralBillService.page(pageable, phone, start, end);
    }

}
