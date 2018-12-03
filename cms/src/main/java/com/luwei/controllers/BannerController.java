package com.luwei.controllers;

import com.luwei.common.enums.type.CollectType;
import com.luwei.services.banner.BannerService;
import com.luwei.services.banner.cms.*;
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
import java.util.List;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Slf4j
@RestController
@Api(tags = "banner模块")
@RequestMapping("api/banner")
public class BannerController {

    @Resource
    private BannerService bannerService;

    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid BannerAddDTO dto) {
        bannerService.save(dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        bannerService.delete(ids);
    }

    @PutMapping
    @ApiOperation("修改")
    public BannerPageVO update(@RequestBody BannerEditDTO dto) {
        return bannerService.update(dto);
    }

    @PutMapping("/show")
    @ApiOperation("显示|隐藏")
    public BannerPageVO showHidden(@RequestParam Integer bannerId) {
        return bannerService.showHidden(bannerId);
    }

    @GetMapping("/one")
    @ApiOperation("根据id查找")
    public BannerPageVO one(@RequestParam Integer bannerId) {
        return bannerService.one(bannerId);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<BannerPageVO> page(@ModelAttribute BannerQueryDTO dto, @PageableDefault Pageable pageable) {
        return bannerService.page(pageable, dto);
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public List<BannerPageVO> list(@ModelAttribute BannerQueryDTO dto) {
        return bannerService.list(dto);
    }

    @GetMapping("/search")
    @ApiOperation("根据名称查找活动或商品")
    public List<GoodsActivityVO> search(@RequestParam(required = false) String name, @RequestParam CollectType type) {
        return bannerService.searchGoodsOrActivity(name, type);
    }


}
