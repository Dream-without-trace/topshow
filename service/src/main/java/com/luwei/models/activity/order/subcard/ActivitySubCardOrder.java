package com.luwei.models.activity.order.subcard;


import lombok.Data;

import javax.persistence.*;

/**
 * @program: topshow
 * @description: 活动次订单
 * @author: ZhangHongJie
 * @create: 2018-12-08 23:44
 **/
@Data
@Entity
public class ActivitySubCardOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activitySubCardOrderId;

    @Column(columnDefinition = "int(11) not null default 0 comment '活动次卡id'")
    private Integer activitySubCard;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户id'")
    private Integer userId;

    @Column(columnDefinition = "int(11) not null default 0 comment '活动id'")
    private Integer activityId;

    @Column(columnDefinition = "int(11) not null default 0 comment '订单总价'")
    private Integer total;

    @Column(columnDefinition = "varchar(64) default '' comment '外部订单号'")
    private String outTradeNo;

    @Column(columnDefinition = "varchar(64) not null default 0 comment '标题'")
    private String title;

    @Column(columnDefinition = "text comment '封面'")
    private String picture;

    @Column(columnDefinition = "text comment '描述'")
    private String detail;

    @Column(columnDefinition = "int(11) not null default 0 comment '总次数'")
    private Integer totolFrequency;

    @Column(columnDefinition = "int(11) not null default 0 comment '已用次数'")
    private Integer usefrequency;

    @Column(columnDefinition = "int(11) not null default 0 comment '下单时间'")
    private Integer createTime;

    @Column(columnDefinition = "int(11) not null default 0 comment '支付时间'")
    private Integer payTime;

    @Column(columnDefinition = "tinyint(1) not null default 1 comment '活动次卡订单状态 ：1 未支付，2：已支付'")
    private Integer status;


    public ActivitySubCardOrder() {
    }

    public ActivitySubCardOrder(Integer activitySubCard, Integer userId, Integer activityId, Integer total, String outTradeNo,
                                String activityTitle, String picture, String detail, Integer totolFrequency, Integer usefrequency,
                                Integer createTime, Integer payTime, Integer status) {
        this.activitySubCard = activitySubCard;
        this.userId = userId;
        this.activityId = activityId;
        this.total = total;
        this.outTradeNo = outTradeNo;
        this.title = activityTitle;
        this.picture = picture;
        this.detail = detail;
        this.totolFrequency = totolFrequency;
        this.usefrequency = usefrequency;
        this.createTime = createTime;
        this.payTime = payTime;
        this.status = status;
    }
}
