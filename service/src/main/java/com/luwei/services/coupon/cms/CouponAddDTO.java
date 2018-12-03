package com.luwei.services.coupon.cms;

import com.luwei.common.enums.status.CouponStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Data
@ApiModel
public class CouponAddDTO implements Serializable {

    @NotNull
    @ApiModelProperty("优惠券名称")
    private String name;

    @NotNull
    @ApiModelProperty("图片")
    private String picture;

    @NotNull
    @ApiModelProperty("介绍")
    private String description;

    @NotNull
    @ApiModelProperty("状态(上架|下架)")
    private CouponStatus status;

    @NotNull
    @ApiModelProperty("优惠券价格")
    private Integer price;

    @NotNull
    @ApiModelProperty("折扣")
    private Integer discount;

}
