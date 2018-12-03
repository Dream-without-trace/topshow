package com.luwei.services.order.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-25
 **/
@Data
@ApiModel
public class DeliverDTO {

    @ApiModelProperty("商家id")
    private Integer storeId;

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("物流公司")
    private String logisticsCompany;

    @ApiModelProperty("快递单号")
    private String expressNumber;

}
