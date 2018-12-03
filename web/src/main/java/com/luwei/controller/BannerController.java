package com.luwei.controller;

import com.luwei.common.enums.type.BannerType;
import com.luwei.services.banner.BannerService;
import com.luwei.services.banner.web.BannerWebVO;
import io.swagger.annotations.Api;
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

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Slf4j
@RestController
@Api(tags = "banner模块")
@RequestMapping("/api/banner")
public class BannerController {

    @Resource
    private BannerService bannerService;

    @GetMapping
    @ApiOperation("获取banner")
    public Page<BannerWebVO> homePage(@PageableDefault Pageable pageable,
                                      @RequestParam BannerType type,
                                      @RequestParam(required = false) Integer areaId) {
        return bannerService.homePage(type, areaId, pageable);
    }

}
