package com.luwei.services.shopping.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-28
 **/
@Data
@ApiModel
public class ShoppingEditDTO {

    @NotNull(message = "购物车id不能为空")
    @ApiModelProperty("购物车id")
    private Integer shoppingId;

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty("用户id")
    private Integer userId;

    @NotNull(message = "商品数量不能为空")
    @ApiModelProperty("商品数量")
    private Integer count;

}
