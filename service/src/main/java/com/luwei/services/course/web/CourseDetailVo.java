package com.luwei.services.course.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: topshow
 * @description: 课程详情
 * @author: ZhangHongJie
 * @create: 2018-12-03 16:15
 **/
@Data
@ApiModel
public class CourseDetailVo {

    @ApiModelProperty("课程ID")
    private Integer courseId;

    @ApiModelProperty("课程名称")
    private String title;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("日期")
    private String startDate;

    @ApiModelProperty("是否可报名：1：可以报名，2：不可报名")
    private Integer isSignUp;

}
