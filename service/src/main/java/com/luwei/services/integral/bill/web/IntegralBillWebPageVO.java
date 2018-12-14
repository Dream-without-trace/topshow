package com.luwei.services.integral.bill.web;

import com.luwei.common.enums.type.BillType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-26
 **/
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

    public Integer getIntegralBillId() {
        return integralBillId;
    }

    public void setIntegralBillId(Integer integralBillId) {
        this.integralBillId = integralBillId;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public BillType getBillType() {
        return billType;
    }

    public void setBillType(BillType billType) {
        this.billType = billType;
    }

    public String getCreateTime() {
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            return sdf.format(createTime);
        }
        return "";
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
