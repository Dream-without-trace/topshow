package com.luwei.services.refund.cms;

import com.luwei.common.enums.status.RefundStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-20
 **/
@Data
@ApiModel
public class RefundPageVO implements Serializable {

    @ApiModelProperty("退款id")
    private Integer refundId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品数量")
    private Integer goodsCount;

    @ApiModelProperty("退款总价")
    private Integer total;

    @ApiModelProperty("订单号")
    private String orderOutNo;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("收货地址")
    private String address;

    @ApiModelProperty("退款状态")
    private RefundStatus status;

}
