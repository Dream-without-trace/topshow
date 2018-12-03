package com.luwei.services.refund.cms;

import com.luwei.common.enums.status.RefundStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-20
 **/
@Data
@ApiModel
public class RefundEditDTO {

    @NotNull
    @ApiModelProperty("退款id")
    private Integer refundId;

    @NotNull
    @ApiModelProperty("商家id")
    private Integer storeId;

    @NotNull
    @ApiModelProperty("状态")
    private RefundStatus status;


}
