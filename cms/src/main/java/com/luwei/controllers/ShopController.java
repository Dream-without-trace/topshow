package com.luwei.controllers;

import com.luwei.common.Response;
import com.luwei.services.area.cms.AreaDTO;
import com.luwei.services.shop.ShopService;
import com.luwei.services.shop.cms.ShopAddDTO;
import com.luwei.services.shop.cms.ShopPageVo;
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
import java.util.ArrayList;
import java.util.Set;

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

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<ShopPageVo> page(@RequestParam(required = false) String title,
                                 @PageableDefault Pageable pageable) {
        return shopService.page(pageable, title);
    }

    @PutMapping
    @ApiOperation("修改")
    public Response update(@RequestBody ShopAddDTO dto) {
        return shopService.update(dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        shopService.delete(ids);
    }


}
