package com.luwei.controller;

import com.luwei.services.shop.ShopService;
import com.luwei.services.shop.web.ShopDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: topshow
 * @description: 门店Controller
 * @author: ZhangHongJie
 * @create: 2018-12-03 15:48
 **/
@Slf4j
@RestController
@Api(tags = "门店模块")
@RequestMapping("/api/shop")
public class ShopController {

    @Resource
    private ShopService shopService;

    @GetMapping("/detail")
    @ApiOperation("详情")
    public ShopDetailVO activityWebDetail(@RequestParam Integer shopId, @RequestParam Integer userId) {
        return shopService.shopWebDetail(shopId, userId);
    }

}
