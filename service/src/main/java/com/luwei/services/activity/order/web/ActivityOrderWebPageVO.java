package com.luwei.services.activity.order.web;

import com.luwei.common.enums.status.ActivityOrderStatus;
import com.luwei.common.enums.type.FlagType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-27
 **/
@Data
@ApiModel
public class ActivityOrderWebPageVO {

    @ApiModelProperty("活动订单id")
    private Integer activityOrderId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("活动id")
    private Integer activityId;

    @ApiModelProperty("活动标题")
    private String activityTitle;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("活动图片")
    private String picture;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("活动状态")
    private ActivityOrderStatus status;

    @ApiModelProperty("是否评价")
    private FlagType flagType;

    public ActivityOrderWebPageVO() {
    }

    public ActivityOrderWebPageVO(Integer activityOrderId, Integer userId, Integer activityId, String activityTitle, String city, String province, String picture, Date startTime, ActivityOrderStatus status, FlagType flagType) {
        this.activityOrderId = activityOrderId;
        this.userId = userId;
        this.activityId = activityId;
        this.activityTitle = activityTitle;
        this.city = city;
        this.province = province;
        this.picture = picture;
        this.startTime = startTime;
        this.status = status;
        this.flagType = flagType;
    }
}
