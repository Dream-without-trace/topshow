package com.luwei.services.goods.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-29
 **/
@Data
@ApiModel
public class GoodsSimpleVO {

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("商品图片")
    private String picture;

    @ApiModelProperty("规格id")
    private Integer specificationId;

    @ApiModelProperty("规格名称")
    private String specificationName;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品数量")
    private Integer count;

    @ApiModelProperty("商品价格")
    private Integer price;

}
