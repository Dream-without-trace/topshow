package com.luwei.services.activity.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-14
 **/
@Data
@ApiModel
public class ActivityApplyVO {

    @ApiModelProperty("活动id")
    private Integer activityId;

    @ApiModelProperty("活动标题")
    private String title;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("活动图片")
    private String picture;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("价格")
    private Integer price;

}
