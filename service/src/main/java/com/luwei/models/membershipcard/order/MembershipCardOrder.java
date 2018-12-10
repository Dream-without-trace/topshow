package com.luwei.models.membershipcard.order;

import com.luwei.common.enums.status.ActivityOrderStatus;
import com.luwei.common.enums.status.MembershipCardOrderStatus;
import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: topshow
 * @description: 会员卡订单
 * @author: ZhangHongJie
 * @create: 2018-12-03 19:42
 **/
@Data
@Entity
public class MembershipCardOrder extends IdEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer membershipCardOrderId;

    @Column(columnDefinition = "int(11) not null default 0 comment '门店id'")
    private Integer shopId;

    @Column(columnDefinition = "int(11) not null default 0 comment '地区ID'")
    private Integer areaId;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户id'")
    private Integer userId;

    @Column(columnDefinition = "int(11) not null default 0 comment '会员卡id'")
    private Integer membershipCardId;

    @Column(columnDefinition = "int(11) not null default 0 comment '订单总价'")
    private Integer total;

    @Column(columnDefinition = "varchar(64) default '' comment '外部订单号'")
    private String outTradeNo;

    @Column(columnDefinition = "int(11) not null default 0 comment '有效时间单位是天'")
    private Integer effective;

    @Column(columnDefinition = "varchar(64) not null default '' comment '会员卡等级名称'")
    private String title;

    @Column(columnDefinition = "text comment '会员卡等级封面'")
    private String picture;

    @Column(columnDefinition = "text comment '会员卡等级描述'")
    private String detail;

    @Column(columnDefinition = "text comment '会员权益'")
    private String memberBenefits;

    @Column(columnDefinition = "timestamp null comment '支付时间'")
    private Date payTime;

    @Column(columnDefinition = "tinyint(2) not null default 0 comment '会员卡订单状态'")
    private MembershipCardOrderStatus status;

    public MembershipCardOrder() {
    }

    public MembershipCardOrder(Integer shopId, Integer areaId, Integer userId,
                               Integer membershipCardId, Integer total, String outTradeNo, Integer effective, String title, String picture, String detail, String memberBenefits, Date payTime, MembershipCardOrderStatus status) {
        this.shopId = shopId;
        this.areaId = areaId;
        this.userId = userId;
        this.membershipCardId = membershipCardId;
        this.total = total;
        this.outTradeNo = outTradeNo;
        this.effective = effective;
        this.title = title;
        this.picture = picture;
        this.detail = detail;
        this.memberBenefits = memberBenefits;
        this.payTime = payTime;
        this.status = status;
    }
}
