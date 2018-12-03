package com.luwei.controllers;

import com.luwei.services.shop.ShopService;
import com.luwei.services.shop.cms.ShopAddDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: topshow
 * @description: 门店Controller
 * @author: ZhangHongJie
 * @create: 2018-12-03 15:06
 **/
@Slf4j
@RestController
@Api(tags = "门店模块")
@RequestMapping("api/shop")
public class ShopController {

    @Resource
    private ShopService shopService;


    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid ShopAddDTO dto) {
        shopService.save(dto);
    }


}
