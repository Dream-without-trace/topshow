package com.luwei.controllers;

import com.luwei.common.Response;
import com.luwei.services.area.cms.AreaDTO;
import com.luwei.services.course.CourseService;
import com.luwei.services.course.cms.CourseDTO;
import com.luwei.services.course.cms.CourseOrderCMSPageVo;
import com.luwei.services.course.cms.CoursePageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

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
    public void save(@RequestBody @Valid CourseDTO dto) {
        courseService.save(dto);
    }


    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<CoursePageVo> page(@RequestParam(required = false) String title,
                                   @PageableDefault Pageable pageable) {
        return courseService.page(pageable, title);
    }

    @PutMapping
    @ApiOperation("修改")
    public Response update(@RequestBody CourseDTO dto) {
        return courseService.update(dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        courseService.delete(ids);
    }


    @GetMapping("/order/page")
    @ApiOperation("分页")
    public Page<CourseOrderCMSPageVo> courseOrderPage(@RequestParam(required = false) String phone,
                                                      @PageableDefault Pageable pageable) {
        return courseService.courseOrderPage(pageable, phone);
    }

    @GetMapping("/order/check")
    @ApiOperation("验单")
    public Response checkCourseOrder(@RequestParam Integer courseEnrolmentId){
        return courseService.checkCourseOrder(courseEnrolmentId);
    }

}
