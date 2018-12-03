package com.luwei.services.order.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-04
 **/
@Data
@ApiModel
public class GoodsOrderPayDTO {

    @NotNull
    @ApiModelProperty("订单列表")
    private Set<Integer> orderIds;

    @NotNull
    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户优惠券id")
    private Integer couponId;

    @NotNull
    @ApiModelProperty
    private String openId;

}
