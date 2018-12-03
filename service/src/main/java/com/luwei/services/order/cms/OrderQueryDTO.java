package com.luwei.services.order.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Data
@ApiModel
public class OrderQueryDTO {

    @NotNull
    @ApiModelProperty("商户id")
    private Integer storeId;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("开始时间")
    private Date start;

    @ApiModelProperty("结束时间")
    private Date end;


}
