package com.luwei.controller;

import com.luwei.services.order.web.AffirmOrderVO;
import com.luwei.services.order.web.OrderAddDTO;
import com.luwei.services.shopping.ShoppingService;
import com.luwei.services.shopping.web.ShoppingAddDTO;
import com.luwei.services.shopping.web.ShoppingEditDTO;
import com.luwei.services.shopping.web.ShoppingPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @since 2018-08-28
 **/
@Slf4j
@RestController
@Api(tags = "购物车模块")
@RequestMapping("/api/shopping")
public class ShoppingController {

    @Resource
    private ShoppingService shoppingService;

    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid ShoppingAddDTO dto) {
        shoppingService.save(dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam Set<Integer> ids, Integer userId) {
        shoppingService.delete(ids, userId);
    }

    @PutMapping
    @ApiOperation("修改购物车商品数量")
    public ShoppingPageVO update(@RequestBody @Valid ShoppingEditDTO dto) {
        return shoppingService.update(dto);
    }


    @PostMapping("/clearing")
    @ApiOperation("购物车结算")
    public AffirmOrderVO clearing(@RequestBody @Valid OrderAddDTO dto) {
        return shoppingService.clearing(dto);
    }


    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<ShoppingPageVO> page(@PageableDefault Pageable pageable, @RequestParam Integer userId) {
        return shoppingService.page(pageable, userId);
    }


}
