package com.luwei.controller;

import com.aliyuncs.exceptions.ClientException;
import com.luwei.common.Response;
import com.luwei.services.activity.web.ActivityWebListVO;
import com.luwei.services.activity.web.HandpickVO;
import com.luwei.services.course.CourseService;
import com.luwei.services.course.web.CourseEnrolmentWebListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: topshow
 * @description: 课程controller
 * @author: ZhangHongJie
 * @create: 2018-12-07 18:06
 **/
@Slf4j
@RestController
@Api(tags = "课程模块")
@RequestMapping("/api/course")
public class CourseController {


    @Resource
    private CourseService courseService;

    @PostMapping("/enrolment")
    @ApiOperation("报名课程")
    public Response enrolment(@RequestParam Integer shopId, @RequestParam Integer courseId, @RequestParam Integer userId) throws ClientException {
        courseService.enrolment(shopId, courseId,userId);
       return Response.success("报名成功！");
    }

    @GetMapping("/enrolmentCourse")
    @ApiOperation("报名课程列表")
    public Page<CourseEnrolmentWebListVO> enrolmentCourseList(@RequestParam Integer userId,@RequestParam Integer isInspectTicket,
                                                    @PageableDefault Pageable pageable) {
        return courseService.enrolmentCourseList(userId, isInspectTicket,pageable);
    }


}
