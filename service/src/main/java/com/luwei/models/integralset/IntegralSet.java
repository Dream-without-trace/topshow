package com.luwei.models.integralset;

import lombok.Data;

import javax.persistence.*;

/**
 * @program: topshow
 * @description: 积分设置
 * @author: ZhangHongJie
 * @create: 2018-12-07 23:23
 **/
@Data
@Entity
public class IntegralSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer integralSetId;


    @Column(columnDefinition = "int(6) not null default 5 comment '首次登陆赠送积分'")
    private Integer firstLoginIntegral;

    @Column(columnDefinition = "int(6) not null default 5 comment '绑定手机号赠送积分'")
    private Integer bindPhoneIntegral;

    @Column(columnDefinition = "int(6) not null default 50 comment '购买会员卡赠送积分'")
    private Integer purchaseMembershipCardIntegral;

    @Column(columnDefinition = "int(6) not null default 2 comment '到店签到赠送积分'")
    private Integer shopCheckInIntegral;

    @Column(columnDefinition = "int(6) not null default 1 comment '每日签到积分'")
    private Integer dailyCheckInIntegral;

    @Column(columnDefinition = "int(6) not null default 30 comment '完成指定任务赠送积分'")
    private Integer taskFinishIntegral;

    @Column(columnDefinition = "int(6) not null default 50 comment '邀请用户赠送积分'")
    private Integer inviteGiftIntegral;

    @Column(columnDefinition = "int(6) not null default 63 comment '开通会员，信息填写，签到赠送积分'")
    private Integer multipleConditionsCheckInIntegral;

    @Column(columnDefinition = "int(6) not null default 5 comment '连续签到7天额外赠送积分'")
    private Integer continuousSevenCheckInIntegral;

    @Column(columnDefinition = "int(6) not null default 10 comment '连续签到15天额外赠送积分'")
    private Integer continuousFifteenCheckInIntegral;

    @Column(columnDefinition = "int(6) not null default 15 comment '连续签到30天额外赠送积分'")
    private Integer continuousThirtyCheckInIntegral;



    @Column(columnDefinition = "text comment '用户分享描述'")
    private String shareDescription;
}
