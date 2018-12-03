package com.luwei.services.shopping.web;

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
public class ShoppingPageVO {

    @ApiModelProperty("购物车id")
    private Integer shoppingId;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("商家id")
    private Integer storeId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("规格名称")
    private String specificationName;

    @ApiModelProperty("规格id")
    private Integer specificationId;

    @ApiModelProperty("规格价格")
    private Integer price;

    @ApiModelProperty("图片")
    private String picture;

    @ApiModelProperty("商品数量")
    private Integer count;

}
