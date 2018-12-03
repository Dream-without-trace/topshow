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
public class ShoppingAddDTO {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("商家id")
    private Integer storeId;

    @ApiModelProperty("规格id")
    private Integer specificationId;

    @ApiModelProperty("商品数量")
    private Integer count;


}
