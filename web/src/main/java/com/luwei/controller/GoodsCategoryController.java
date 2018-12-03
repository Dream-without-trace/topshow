package com.luwei.controller;

import com.luwei.services.category.CategoryService;
import com.luwei.services.category.web.CategoryListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Slf4j
@RestController
@Api(tags = "商品分类模块")
@RequestMapping("/api/goods/category")
public class GoodsCategoryController {


    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    @ApiOperation("所有商品分类")
    public List<CategoryListVO> list() {
        return categoryService.webList();
    }

}
