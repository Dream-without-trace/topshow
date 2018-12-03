package com.luwei.controller;

import com.luwei.common.enums.type.BillType;
import com.luwei.services.integral.bill.IntegralBillService;
import com.luwei.services.integral.bill.web.IntegralBillWebPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Slf4j
@RestController
@Api(tags = "积分模块")
@RequestMapping("/api/integral")
public class IntegralController {

    @Resource
    private IntegralBillService integralBillService;

    @GetMapping("/page")
    @ApiOperation("分页查询")

    public Page<IntegralBillWebPageVO> page(@PageableDefault Pageable pageable, @RequestParam BillType billType, @RequestParam Integer userId) {
        return integralBillService.page(pageable, userId, billType);
    }


}
