package com.luwei.controller;

import com.luwei.services.activity.category.ActivityCategoryService;
import com.luwei.services.activity.category.web.ActivityCategoryListVO;
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
@Api(tags = "活动分类模块")
@RequestMapping("/api/category")
public class ActivityCategoryController {

    @Resource
    private ActivityCategoryService activityCategoryService;

    @GetMapping("/list")
    @ApiOperation("所有活动分类")
    public List<ActivityCategoryListVO> list() {
        return activityCategoryService.list();
    }


}
