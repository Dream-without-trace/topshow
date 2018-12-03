package com.luwei.models.activity;

import com.luwei.common.enums.status.ActivityStatus;
import com.luwei.common.enums.type.ActivityType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.enums.type.TicketType;
import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Leone
 * @since 2018-08-01
 **/
@Data
@Entity
public class Activity extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;

    @Column(columnDefinition = "int(11) not null default -1 comment '活动分类id'")
    private Integer activityCategoryId;

    @Column(columnDefinition = "int(11) not null default -1 comment '活动系列id'")
    private Integer activitySeriesId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '活动标题'")
    private String title;

    @Column(columnDefinition = "timestamp not null default current_timestamp comment '开始时间'")
    private Date startTime;

    @Column(columnDefinition = "timestamp not null default current_timestamp comment '结束时间'")
    private Date endTime;

    @Column(columnDefinition = "varchar(255) not null default '' comment '活动详细地点'")
    private String address;

    @Column(columnDefinition = "varchar(24) not null default '' comment '活动城市'")
    private String city;

    @Column(columnDefinition = "varchar(24) not null default '' comment '省'")
    private String province;

    @Column(columnDefinition = "int(11) not null default -1 comment '地区id'")
    private Integer areaId;

    @Column(columnDefinition = "int(11) not null default 0 comment '活动价格'")
    private Integer price;

    @Column(columnDefinition = "text comment '活动封面'")
    private String picture;

    @Column(columnDefinition = "smallint(11) default 0 comment '活动状态'")
    private ActivityStatus status;

    @Column(columnDefinition = "text comment '活动详情描述'")
    private String detail;

    @Column(columnDefinition = "text comment '往期风采'")
    private String previous;

    @Column(columnDefinition = "varchar(255) default '' comment '购票须知'")
    private String notice;

    @Column(columnDefinition = "text comment '退款说明'")
    private String refundExplain;

    @Enumerated
    @Column(columnDefinition = "tinyint(11) default 0 comment '是否推荐'")
    private FlagType recommend;

    @Enumerated
    @Column(columnDefinition = "tinyint(11) default 0 comment '票券类型'")
    private TicketType ticketType;

    @Enumerated
    @Column(columnDefinition = "tinyint(11) default 0 comment '活动类型'")
    private ActivityType activityType;

}
