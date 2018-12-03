package com.luwei.services.coupon.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-26
 **/
@Data
@ApiModel
public class CouponDetailWebVO {

    @ApiModelProperty("优惠券id")
    private Integer couponId;

    @ApiModelProperty("优惠券名称")
    private String name;

    @ApiModelProperty("优惠券价格")
    private Integer price;

    @ApiModelProperty("图片")
    private String picture;

    @ApiModelProperty("折扣")
    private Integer discount;

    @ApiModelProperty("详情")
    private String description;

}
