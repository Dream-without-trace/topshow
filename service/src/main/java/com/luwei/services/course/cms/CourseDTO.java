package com.luwei.services.course.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: topshow
 * @description: 课程
 * @author: ZhangHongJie
 * @create: 2018-12-09 20:48
 **/
@Data
@ApiModel
public class CourseDTO {
    @ApiModelProperty("课程Id")
    private Integer courseId;
    @NotNull(message = "门店ID不能为空")
    @ApiModelProperty("门店ID")
    private Integer shopId;
    @NotNull(message = "标题不能为空")
    @ApiModelProperty("标题")
    private String title;
    @NotNull(message = "课程日期不能为空")
    @ApiModelProperty("课程日期")
    private String startDate;
    @NotNull(message = "开始时间不能为空")
    @ApiModelProperty("开始时间")
    private String startTime;
    @NotNull(message = "结束时间不能为空")
    @ApiModelProperty("结束时间")
    private String endTime;
    @NotNull(message = "最大报名人数不能为空")
    @ApiModelProperty("最大报名人数")
    private Integer maxNum;
    @NotNull(message = "封面不能为空")
    @ApiModelProperty("封面")
    private String picture;
    @NotNull(message = "描述不能为空")
    @ApiModelProperty("描述")
    private String description;



}
