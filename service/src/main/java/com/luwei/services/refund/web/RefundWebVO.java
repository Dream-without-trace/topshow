package com.luwei.services.refund.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-28
 **/
@Data
@ApiModel
public class RefundWebVO {

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("商品数量")
    private Integer goodsCount;

    @ApiModelProperty("外部订单号")
    private String orderOutNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品图片")
    private String goodsPicture;

    @ApiModelProperty("规格名称")
    private String specificationName;

    @ApiModelProperty("规格id")
    private Integer specificationId;

    @ApiModelProperty("原因")
    private String cause;

    @ApiModelProperty("选填原因")
    private String optionCause;

    @ApiModelProperty("退款金额")
    private Integer total;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("退款id")
    private Integer refundId;

}
