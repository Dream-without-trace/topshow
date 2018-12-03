package com.luwei.services.refund.cms;

import com.luwei.common.enums.status.RefundStatus;
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
public class RefundQueryDTO {

    @ApiModelProperty("订单号")
    private String orderOutNo;

    @ApiModelProperty("售后状态")
    private RefundStatus status;


}
