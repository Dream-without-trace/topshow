package com.luwei.models.membershipcard;

import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @program: topshow
 * @description: 会员卡
 * @author: ZhangHongJie
 * @create: 2018-12-03 16:02
 **/
@Data
@Entity
public class MembershipCard extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer membershipCardId;

    @Column(columnDefinition = "int(11) not null default 0 comment '有效时间单位是天'")
    private Integer effective;

    @Column(columnDefinition = "int(11) not null default 0 comment '地区ID'")
    private Integer areaId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '会员卡等级标题'")
    private String title;

    @Column(columnDefinition = "text comment '会员卡等级封面'")
    private String picture;

    @Column(columnDefinition = "text comment '会员卡等级描述'")
    private String detail;

    @Column(columnDefinition = "text comment '会员权益'")
    private String memberBenefits;

    @Column(columnDefinition = "int(11) not null default 0 comment '价格'")
    private Integer price;



}
