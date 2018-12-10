package com.luwei.services.course.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: topshow
 * @description: 报名课程列表
 * @author: ZhangHongJie
 * @create: 2018-12-10 23:00
 **/
@Data
@ApiModel
public class CourseEnrolmentWebListVO {

    @ApiModelProperty("报名课程Id")
    private Integer courseEnrolmentId;

    @ApiModelProperty("门店名称")
    private String shopName;

    @ApiModelProperty("课程名称")
    private String title;

    @ApiModelProperty("上课日期")
    private String startDate;

    @ApiModelProperty("上课时间")
    private String startTime;

    @ApiModelProperty("下课时间")
    private String endTime;

    @ApiModelProperty("报名时间")
    private String enrollTime;

    @ApiModelProperty("课程封面")
    private String picture;

    @ApiModelProperty("是否已验票 ：1，未验票，2，已验票")
    private Integer isInspectTicket;


    public CourseEnrolmentWebListVO() {
    }

    public CourseEnrolmentWebListVO(Integer courseEnrolmentId, String shopName, String title, String startDate,
           String startTime, String endTime, String enrollTime, String picture, Integer isInspectTicket) {
        this.courseEnrolmentId = courseEnrolmentId;
        this.shopName = shopName;
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.enrollTime = enrollTime;
        this.picture = picture;
        this.isInspectTicket = isInspectTicket;
    }
}
