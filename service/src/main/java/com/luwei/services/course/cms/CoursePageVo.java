package com.luwei.services.course.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * @program: topshow
 * @description: 课程列表
 * @author: ZhangHongJie
 * @create: 2018-12-09 19:26
 **/
@Data
@ApiModel
public class CoursePageVo {

    @ApiModelProperty("课程id")
    private Integer courseId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("门店id")
    private Integer shopId;

    @ApiModelProperty("门店名称")
    private String areaName;

    @ApiModelProperty("门店名称")
    private String shopName;

    @ApiModelProperty("课程日期")
    private String startDate;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("最大报名人数")
    private Integer maxNum;

    @ApiModelProperty("封面")
    private String picture;

    @ApiModelProperty("描述")
    private String description;

}
