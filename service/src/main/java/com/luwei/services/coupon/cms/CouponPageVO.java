package com.luwei.services.coupon.cms;

import com.luwei.common.enums.status.CouponStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Data
@ApiModel
public class CouponPageVO implements Serializable {

    @ApiModelProperty("优惠券id")
    private Integer couponId;

    @ApiModelProperty("优惠券名称")
    private String name;

    @ApiModelProperty("图片")
    private String picture;

    @ApiModelProperty("折扣")
    private Integer discount;

    @ApiModelProperty("优惠券介绍")
    private String description;

    @ApiModelProperty("时间")
    private Date createTime;

    @ApiModelProperty("状态(上架|下架)")
    private CouponStatus status;

    @ApiModelProperty("优惠券价格")
    private Integer price;

}
