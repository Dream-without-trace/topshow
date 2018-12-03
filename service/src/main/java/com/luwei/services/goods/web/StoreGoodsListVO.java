package com.luwei.services.goods.web;

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
public class StoreGoodsListVO {

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品图片")
    private String picture;

    @ApiModelProperty("商品价格")
    private Integer price;


    public StoreGoodsListVO() {
    }

    public StoreGoodsListVO(Integer goodsId, String name, String picture, Integer price) {
        this.goodsId = goodsId;
        this.name = name;
        this.picture = picture;
        this.price = price;
    }
}
