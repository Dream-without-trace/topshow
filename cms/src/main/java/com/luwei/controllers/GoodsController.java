package com.luwei.controllers;

import com.luwei.services.goods.GoodsService;
import com.luwei.services.goods.cms.GoodsAddDTO;
import com.luwei.services.goods.cms.GoodsEditDTO;
import com.luwei.services.goods.cms.GoodsPageVO;
import com.luwei.services.goods.cms.GoodsQueryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Slf4j
@RestController
@Api(tags = "商品模块")
@RequestMapping("api/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid GoodsAddDTO dto) {
        goodsService.save(dto);
    }

    @PutMapping("/upDown")
    @ApiOperation("上架|下架")
    public GoodsPageVO update(@RequestParam Integer goodsId) {
        return goodsService.update(goodsId);
    }

    @PutMapping("/recommend")
    @ApiOperation("推荐")
    public GoodsPageVO recommend(@RequestParam Integer goodsId) {
        return goodsService.recommend(goodsId);
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public Map detail(@RequestParam Integer goodsId) {
        return goodsService.detail(goodsId);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<GoodsPageVO> page(@ModelAttribute GoodsQueryDTO dto, @PageableDefault Pageable pageable) {
        return goodsService.page(pageable, dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam Set<Integer> ids) {
        goodsService.delete(ids);
    }

    @PutMapping
    @ApiOperation("修改")
    public GoodsPageVO update(@RequestBody GoodsEditDTO dto) {
        return goodsService.update(dto);
    }


}
