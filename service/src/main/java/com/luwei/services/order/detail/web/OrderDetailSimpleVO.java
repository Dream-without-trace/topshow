package com.luwei.services.order.detail.web;

import com.luwei.common.enums.type.FlagType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-06
 **/
@Data
@ApiModel
public class OrderDetailSimpleVO {

    @ApiModelProperty("订单详情id")
    private Integer orderDetailId;

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("规格图片")
    private String picture;

    @ApiModelProperty("商品id")
    private String name;

    @ApiModelProperty("是否评价")
    private FlagType flagType;


}
