package com.luwei.services.activity.cms;

import com.luwei.common.enums.status.ActivityOrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: topshow
 * @description: cms活动订单
 * @author: ZhangHongJie
 * @create: 2018-12-12 11:37
 **/
@ApiModel
public class ActivityOrderCMSPageVO {


    @ApiModelProperty("订单id")
    private Integer activityOrderId;

    @ApiModelProperty("订单号")
    private String outTradeNo;

    @ApiModelProperty("用户名")
    private String nickname;

    @ApiModelProperty("用户头像")
    private String avatarUrl;

    @ApiModelProperty("用户手机号")
    private String phone;

    @ApiModelProperty("活动名称")
    private String activityTitle;

    @ApiModelProperty("下单时间")
    private Date createTime;

    @ApiModelProperty("订单状态")
    private ActivityOrderStatus status;

    public ActivityOrderCMSPageVO() {
    }

    public ActivityOrderCMSPageVO(Integer activityOrderId, String outTradeNo, String nickname, String avatarUrl, String phone, String activityTitle, Date createTime, ActivityOrderStatus status) {
        this.activityOrderId = activityOrderId;
        this.outTradeNo = outTradeNo;
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.phone = phone;
        this.activityTitle = activityTitle;
        this.createTime = createTime;
        this.status = status;
    }

    public Integer getActivityOrderId() {
        return activityOrderId;
    }

    public void setActivityOrderId(Integer activityOrderId) {
        this.activityOrderId = activityOrderId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getCreateTime() {
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(createTime);
        }
        return "";
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public ActivityOrderStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityOrderStatus status) {
        this.status = status;
    }
}
