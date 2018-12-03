package com.luwei.models.evaluate;

import com.luwei.common.enums.level.EvaluateLevel;
import com.luwei.common.enums.type.EvaluateType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>评价
 *
 * @author Leone
 * @since 2018-08-02
 **/
@Data
@Entity
public class Evaluate extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer evaluateId;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户id'")
    private Integer userId;

    @Column(columnDefinition = "int(11) not null default 0 comment '商品id或活动id或门店id'")
    private Integer tripartiteId;

    @Enumerated
    @Column(columnDefinition = "tinyint(1) not null default 0 comment '评分等级'")
    private EvaluateLevel level;

    @Enumerated
    @Column(columnDefinition = "tinyint(1) not null default 0 comment '评论类型(活动、商品、门店)'")
    private EvaluateType type;

    @Column(columnDefinition = "varchar(255) not null default '' comment '评论内容'")
    private String content;

    @Column(columnDefinition = "text comment '评论图片'")
    private String picture;

    @Column(columnDefinition = "varchar(255) not null default '' comment '评论的地点'")
    private String address;

    @Column(columnDefinition = "varchar(255) default '' comment '回复内容'")
    private String replyContent;

    @Column(columnDefinition = "timestamp null comment '回复时间'")
    private Date replyTime;

    @Column(columnDefinition = "int(11) default 0 comment '回复管理员id'")
    private Integer managerId;

    @Column(columnDefinition = "int(11) default 0 comment '点赞数'")
    private Integer praise;

    @Column(columnDefinition = "tinyint(11) not null default 0 comment '显示隐藏'")
    private FlagType flagType;

}
