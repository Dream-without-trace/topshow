package com.luwei.controllers;

import com.luwei.services.coupon.CouponService;
import com.luwei.services.coupon.cms.CouponAddDTO;
import com.luwei.services.coupon.cms.CouponEditDTO;
import com.luwei.services.coupon.cms.CouponPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Slf4j
@RestController
@Api(tags = "优惠券模块")
@RequestMapping("api/coupon")
public class CouponController {

    @Resource
    private CouponService couponService;

    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid CouponAddDTO dto) {
        couponService.save(dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        couponService.delete(ids);
    }

    @PutMapping
    @ApiOperation("修改")
    public CouponPageVO update(@RequestBody CouponEditDTO dto) {
        return couponService.update(dto);
    }

    @GetMapping("/one")
    @ApiOperation("根据id查找")
    public CouponPageVO one(@RequestParam Integer couponId) {
        return couponService.one(couponId);
    }

    @PutMapping("/status")
    @ApiOperation("上架|下架")
    public CouponPageVO updateStatus(@RequestParam Integer couponId) {
        return couponService.updateStatus(couponId);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<CouponPageVO> page(@RequestParam(required = false) String name, @PageableDefault Pageable pageable) {
        return this.couponService.page(pageable, name);
    }

}
