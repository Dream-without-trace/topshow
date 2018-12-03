package com.luwei.controllers;

import com.luwei.services.activity.ActivityService;
import com.luwei.services.activity.cms.ActivityAddDTO;
import com.luwei.services.course.CourseService;
import com.luwei.services.course.cms.CourseAddDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: topshow
 * @description: 课程Controller
 * @author: ZhangHongJie
 * @create: 2018-12-03 17:46
 **/
@Slf4j
@RestController
@Api(tags = "课程模块")
@RequestMapping("api/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid CourseAddDTO dto) {
        courseService.save(dto);
    }



}
