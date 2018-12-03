package com.luwei.services.refund.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-21
 **/
@Data
@ApiModel
public class RefundAddDTO {

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("商品数量")
    private Integer goodsCount;

    @ApiModelProperty("规格id")
    private Integer specificationId;

    @ApiModelProperty("原因")
    private String cause;

    @ApiModelProperty("选填原因")
    private String optionCause;

}
