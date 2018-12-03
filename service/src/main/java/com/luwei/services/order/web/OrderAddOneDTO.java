package com.luwei.services.order.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-05
 **/
@Data
@ApiModel
public class OrderAddOneDTO {

    @NotNull
    @ApiModelProperty("用户id")
    private Integer userId;

    @NotNull
    @ApiModelProperty("商品id")
    private Integer goodsId;

    @NotNull
    @ApiModelProperty("商品数量")
    private Integer count;

    @NotNull
    @ApiModelProperty("规格id")
    private Integer specificationId;

    @NotNull
    @ApiModelProperty("商家id")
    private Integer storeId;

}
