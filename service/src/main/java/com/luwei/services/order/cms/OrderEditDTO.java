package com.luwei.services.order.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Data
@ApiModel
public class OrderEditDTO {

    @NotNull(message = "订单id不能为空")
    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("联系人")
    private String username;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("总价")
    private Integer total;

    @ApiModelProperty("收货地址")
    private String address;

}
