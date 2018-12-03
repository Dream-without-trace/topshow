package com.luwei.models.activity.order;

import com.luwei.common.enums.status.ActivityOrderStatus;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.utils.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-23
 **/
@Data
@Entity
public class ActivityOrder extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityOrderId;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户id'")
    private Integer userId;

    @Column(columnDefinition = "int(11) not null default 0 comment '活动id'")
    private Integer activityId;

    @Column(columnDefinition = "int(11) not null default 0 comment '报名数量'")
    private Integer count;

    @Column(columnDefinition = "int(11) not null default 0 comment '订单总价'")
    private Integer total;

    @Column(columnDefinition = "varchar(64) default '' comment '外部订单号'")
    private String outTradeNo;

    @Column(columnDefinition = "varchar(64) not null default 0 comment '活动标题'")
    private String activityTitle;

    @Column(columnDefinition = "varchar(64) not null default 0 comment '活动地址'")
    private String address;

    @Column(columnDefinition = "timestamp not null default current_timestamp comment '开始时间'")
    private Date startTime;

    @Column(columnDefinition = "timestamp not null default current_timestamp comment '结束时间'")
    private Date endTime;

    @Column(columnDefinition = "timestamp null comment '支付时间'")
    private Date payTime;

    @Column(columnDefinition = "tinyint(2) not null default 0 comment '活动订单状态'")
    private ActivityOrderStatus status;

    @Column(columnDefinition = "tinyint(2) not null default 0 comment '是否评价'")
    private FlagType flagType;


}
