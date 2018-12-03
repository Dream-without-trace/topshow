package com.luwei.controllers;

import com.luwei.services.activity.category.ActivityCategoryService;
import com.luwei.services.activity.category.cms.ActivityCategoryAddDTO;
import com.luwei.services.activity.category.cms.ActivityCategoryEditDTO;
import com.luwei.services.activity.category.cms.ActivityCategoryPageVO;
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
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Slf4j
@RestController
@Api(tags = "活动分类模块")
@RequestMapping("api/activity/category")
public class ActivityCategoryController {

    @Resource
    private ActivityCategoryService activityCategoryService;


    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid ActivityCategoryAddDTO dto) {
        activityCategoryService.save(dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        activityCategoryService.delete(ids);
    }

    @PutMapping
    @ApiOperation("修改")
    public ActivityCategoryPageVO update(@RequestBody ActivityCategoryEditDTO dto) {
        return activityCategoryService.update(dto);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<ActivityCategoryPageVO> page(@RequestParam(required = false) String title,
                                             @PageableDefault Pageable pageable) {
        return activityCategoryService.page(pageable, title);
    }

    @GetMapping("/one")
    @ApiOperation("根据id查找")
    public ActivityCategoryPageVO one(@RequestParam Integer id) {
        return activityCategoryService.one(id);
    }

}
