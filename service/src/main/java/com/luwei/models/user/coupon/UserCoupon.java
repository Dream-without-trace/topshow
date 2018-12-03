package com.luwei.models.user.coupon;

import com.luwei.common.enums.type.FlagType;
import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-24
 **/
@Data
@Entity
public class UserCoupon extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userCouponId;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户id'")
    private Integer userId;

    @Column(columnDefinition = "int(11) not null default 0 comment '优惠券id'")
    private Integer couponId;

    @Column(columnDefinition = "tinyint(11) not null default 10 comment '折扣'")
    private Integer discount;

    @Column(columnDefinition = "varchar(255) not null default '' comment '优惠图片'")
    private String picture;

    @Column(columnDefinition = "varchar(64) not null default '' comment '优惠券标题'")
    private String couponTitle;

    @Column(columnDefinition = "tinyint(1) default 0 comment '是否使用'")
    private FlagType flagType;

}
