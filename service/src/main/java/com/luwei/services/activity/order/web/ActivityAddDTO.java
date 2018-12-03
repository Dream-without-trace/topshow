package com.luwei.services.activity.order.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-24
 **/
@Data
@ApiModel
public class ActivityAddDTO {

    @NotNull
    @ApiModelProperty("活动订单id")
    private Integer activityOrderId;

    @NotNull
    @ApiModelProperty("用户id")
    private Integer userId;

    @NotNull
    @ApiModelProperty("优惠券id")
    private Integer couponId;

    @NotNull
    @ApiModelProperty("openid")
    private String openid;

}
