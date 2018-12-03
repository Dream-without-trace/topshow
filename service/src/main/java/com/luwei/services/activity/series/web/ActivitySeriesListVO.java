package com.luwei.services.activity.series.web;

import com.luwei.common.enums.status.ActivityStatus;
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
public class ActivitySeriesListVO {

    @ApiModelProperty("活动id")
    private Integer activityId;

    @ApiModelProperty("活动图片")
    private String picture;

    @ApiModelProperty("活动标题")
    private String title;

    @ApiModelProperty("活动地址")
    private String address;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("活动状态")
    private ActivityStatus status;

}
