package com.luwei.services.coupon.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-24
 **/
@Data
@ApiModel
public class CouponOrderListVO {

    @ApiModelProperty("优惠券id")
    private Integer couponId;

    @ApiModelProperty("优惠券名称")
    private String name;

    @ApiModelProperty("图片")
    private String picture;

    @ApiModelProperty("折扣")
    private Integer discount;

}
