package com.luwei.models.coupon;

import com.luwei.common.enums.status.CouponStatus;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Data
@Entity
public class Coupon extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer couponId;

    @Column(columnDefinition = "varchar(255) not null default '' comment '积分名称'")
    private String name;

    @Column(columnDefinition = "varchar(255) not null default '' comment '图片'")
    private String picture;

    @Column(columnDefinition = "tinyint(11) not null default 10 comment '折扣'")
    private Integer discount;

    @Column(columnDefinition = "text comment '介绍'")
    private String description;

    @Column(columnDefinition = "tinyint(11) not null default -1 comment '状态'")
    private CouponStatus status;

    @Column(columnDefinition = "int(11) not null default 0 comment '可兑换积分'")
    private Integer price;

}
