package com.luwei.controllers;

import com.luwei.services.category.CategoryService;
import com.luwei.services.category.cms.CategoryAddDTO;
import com.luwei.services.category.cms.CategoryEditDTO;
import com.luwei.services.category.cms.CategoryPageVO;
import com.luwei.services.category.cms.CategorySelectVO;
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
@Api(tags = "商品分类模块")
@RequestMapping("api/category")
public class GoodsCategoryController {

    @Resource
    private CategoryService categoryService;

    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid CategoryAddDTO dto) {
        categoryService.save(dto);
    }

    @GetMapping("/list")
    @ApiOperation("所有商品类别列表")
    public List<CategorySelectVO> category() {
        return categoryService.categoryList();
    }

    @PutMapping
    @ApiOperation("修改")
    public void update(@RequestBody CategoryEditDTO dto) {
        categoryService.update(dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        categoryService.delete(ids);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<CategoryPageVO> page(@PageableDefault Pageable pageable) {
        return categoryService.page(pageable, null);
    }

}
