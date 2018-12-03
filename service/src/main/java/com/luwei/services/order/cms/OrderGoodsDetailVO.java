package com.luwei.services.order.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Leone
 * @since 2018-08-10
 **/
@Data
@ApiModel
public class OrderGoodsDetailVO implements Serializable {

    @ApiModelProperty("商品编号")
    private Integer goodsId;

    @ApiModelProperty("商品图片")
    private String picture;

    @ApiModelProperty("商品数量")
    private Integer goodsCount;

    @ApiModelProperty("商品编号")
    private Integer price;

    @ApiModelProperty("商品规格名称")
    private String specificationName;

    @ApiModelProperty("商品规格id")
    private Integer specificationId;

    public OrderGoodsDetailVO() {
    }

    public OrderGoodsDetailVO(Integer goodsId, String picture, Integer goodsCount, Integer price, String specificationName, Integer specificationId) {
        this.goodsId = goodsId;
        this.picture = picture;
        this.goodsCount = goodsCount;
        this.price = price;
        this.specificationName = specificationName;
        this.specificationId = specificationId;
    }

}
