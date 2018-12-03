package com.luwei.services.integral.bill.web;

import com.luwei.common.enums.type.BillType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-17
 **/
@Data
@ApiModel
public class IntegralBillDTO {

    @ApiModelProperty("用户id")
    private Integer userId;

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

    @ApiModelProperty("备注")
    private String remark;


    public IntegralBillDTO() {
    }

    public IntegralBillDTO(Integer userId, String nickname, String phone, Integer integral, Integer nowIntegral, BillType billType, String remark) {
        this.userId = userId;
        this.nickname = nickname;
        this.phone = phone;
        this.integral = integral;
        this.nowIntegral = nowIntegral;
        this.billType = billType;
        this.remark = remark;
    }

}
