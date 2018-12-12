package com.luwei.models.activity.subcard;

import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @program: topshow
 * @description: 活动次卡
 * @author: ZhangHongJie
 * @create: 2018-12-08 23:11
 **/
@Data
@Entity
public class ActivitySubCard extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activitySubCardId;

    @Column(columnDefinition = "int(11) not null default 0 comment '活动id'")
    private Integer activityId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '标题'")
    private String title;

    @Column(columnDefinition = "text comment '封面'")
    private String picture;

    @Column(columnDefinition = "text comment '描述'")
    private String detail;

    @Column(columnDefinition = "int(11) not null default 0 comment '价格'")
    private Integer price;

    @Column(columnDefinition = "int(11) not null default 0 comment '次数'")
    private Integer frequency;
}
