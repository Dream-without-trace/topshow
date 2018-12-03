package com.luwei.models.user.coupon.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-28
 **/
@Data
@ApiModel
public class UserCouponAddDTO {

    @ApiModelProperty("优惠券id")
    private Integer couponId;

    @ApiModelProperty("用户id")
    private Integer userId;

}
