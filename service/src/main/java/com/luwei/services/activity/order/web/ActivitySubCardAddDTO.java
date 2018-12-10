package com.luwei.services.activity.order.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: topshow
 * @description:
 * @author: ZhangHongJie
 * @create: 2018-12-08 23:29
 **/
@Data
@ApiModel
public class ActivitySubCardAddDTO {


    @NotNull
    @ApiModelProperty("活动id")
    private Integer activityId;

    @NotNull
    @ApiModelProperty("用户id")
    private Integer userId;

    @NotNull
    @ApiModelProperty("活动次卡规格ID")
    private Integer couponId;

    @NotNull
    @ApiModelProperty("openid")
    private String openid;

}
