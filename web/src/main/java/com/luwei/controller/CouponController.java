package com.luwei.controller;

import com.luwei.common.Response;
import com.luwei.common.enums.type.SortType;
import com.luwei.models.user.coupon.web.UserCouponAddDTO;
import com.luwei.services.coupon.CouponService;
import com.luwei.services.coupon.web.CouponDetailWebVO;
import com.luwei.services.coupon.web.CouponWebVO;
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
@Api(tags = "优惠券模块")
@RequestMapping("/api/coupon")
public class CouponController {

    @Resource
    private CouponService couponService;


    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Page<CouponWebVO> page(@PageableDefault Pageable pageable, @RequestParam(required = false) SortType price) {
        return couponService.webPage(price, pageable);
    }

    @GetMapping("/detail")
    @ApiOperation("优惠券详情")
    public CouponDetailWebVO detail(@RequestParam Integer couponId) {
        return couponService.detail(couponId);
    }

    @PostMapping("/buy")
    @ApiOperation("兑换优惠券")
    public Response<String> buyCoupon(@RequestBody UserCouponAddDTO dto) {
        return couponService.buyCoupon(dto);
    }


}
