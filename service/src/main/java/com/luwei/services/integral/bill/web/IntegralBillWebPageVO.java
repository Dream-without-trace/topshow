package com.luwei.services.integral.bill.web;

import com.luwei.common.enums.type.BillType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-26
 **/
@Data
@ApiModel
public class IntegralBillWebPageVO {

    @ApiModelProperty("积分收支id")
    private Integer integralBillId;

    @ApiModelProperty("积分")
    private Integer integral;

    @ApiModelProperty("收支类型")
    private BillType billType;

    @ApiModelProperty("消费时间")
    private Date createTime;

    @ApiModelProperty("备注")
    private String remark;
}
