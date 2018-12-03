package com.luwei.controller;

import com.luwei.services.goods.GoodsService;
import com.luwei.services.goods.web.GoodsDetailVO;
import com.luwei.services.goods.web.StoreGoodsListVO;
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
@Api(tags = "商品模块模块")
@RequestMapping("/api/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;


    @GetMapping("/detail")
    @ApiOperation("商品详情")
    public GoodsDetailVO detail(@RequestParam Integer goodsId, @RequestParam(required = false) Integer userId) {
        return goodsService.webGoodsDetail(goodsId, userId);
    }


    @GetMapping("/like")
    @ApiOperation("猜你喜欢")
    public Page<StoreGoodsListVO> guessLike(@RequestParam(required = false) Integer userId, @PageableDefault Pageable pageable) {
        return goodsService.guessLike(userId, pageable);
    }


    @GetMapping("/recommend")
    @ApiOperation("推荐商品")
    public Page<StoreGoodsListVO> recommend(@PageableDefault Pageable pageable) {
        return goodsService.recommendGoods(pageable);
    }

    @GetMapping("/category/page")
    @ApiOperation("根据分类获取商品")
    public Page<StoreGoodsListVO> categoryPage(@RequestParam Integer categoryId, @PageableDefault Pageable pageable) {
        return goodsService.categoryPage(categoryId, pageable);
    }

}
