package com.luwei.services.order.detail.web;

import com.luwei.common.enums.type.FlagType;
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
public class OrderGoodsWebVO {

    @ApiModelProperty("订单详情id")
    private Integer orderDetailId;

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品价格")
    private Integer price;

    @ApiModelProperty("商品数量")
    private Integer count;

    @ApiModelProperty("规格id")
    private Integer specificationId;

    @ApiModelProperty("规格图片")
    private String specificationPicture;

    @ApiModelProperty("规格名称")
    private String specificationName;

    @ApiModelProperty("是否评价")
    private FlagType flagType;


    public OrderGoodsWebVO() {
    }

    public OrderGoodsWebVO(Integer orderDetailId, Integer goodsId, String goodsName, Integer price, Integer count, Integer specificationId, String specificationPicture, String specificationName, FlagType flagType) {
        this.orderDetailId = orderDetailId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.price = price;
        this.count = count;
        this.specificationId = specificationId;
        this.specificationPicture = specificationPicture;
        this.specificationName = specificationName;
        this.flagType = flagType;
    }
}
