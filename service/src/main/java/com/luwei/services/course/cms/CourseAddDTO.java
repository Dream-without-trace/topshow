package com.luwei.services.course.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @program: topshow
 * @description: 课程添加类
 * @author: ZhangHongJie
 * @create: 2018-12-03 17:48
 **/
@Data
@ApiModel
public class CourseAddDTO {

    @NotNull(message = "标题不能为空")
    @ApiModelProperty("标题")
    private String title;


    @NotNull(message = "开始时间不能为空")
    @ApiModelProperty("开始时间")
    private Date startTime;

    @NotNull(message = "结束时间不能为空")
    @ApiModelProperty("结束时间")
    private Date endTime;

    @NotNull(message = "最大报名人数不能为空")
    @ApiModelProperty("最大报名人数")
    private Integer maxNum;

    @NotNull(message = "门店ID不能为空")
    @ApiModelProperty("门店ID")
    private Integer shopId;


}
