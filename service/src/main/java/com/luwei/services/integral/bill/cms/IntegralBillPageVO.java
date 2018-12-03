package com.luwei.services.integral.bill.cms;

import com.luwei.common.enums.type.BillType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Data
@ApiModel
public class IntegralBillPageVO implements Serializable {

    @ApiModelProperty("积分收支id")
    private Integer integralBillId;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户手机")
    private String phone;

    @ApiModelProperty("积分")
    private Integer integral;

    @ApiModelProperty("当前积分")
    private Integer nowIntegral;

    @ApiModelProperty("收支类型")
    private BillType billType;

    @ApiModelProperty("消费时间")
    private Date time;

    @ApiModelProperty("备注")
    private String remark;

}
