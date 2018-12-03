package com.luwei.models.order;

import com.luwei.common.enums.status.OrderStatus;
import com.luwei.common.utils.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Leone
 * @since 2018-08-01
 **/
@Data
@Entity
public class Order extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(columnDefinition = "int(11) default 0 comment '商户id'")
    private Integer storeId;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户id'")
    private Integer userId;

    @Column(columnDefinition = "mediumint(11) default 0 comment '商品数量'")
    private Integer goodsCount;

    @Column(columnDefinition = "varchar(64) default '' comment '内部订单号'")
    private String orderOutNo;

    @Column(columnDefinition = "varchar(64) default '' comment '外部订单号'")
    private String outTradeNo;

    @Column(columnDefinition = "int(11) default 0 comment '订单商品总额(单位为分)'")
    private Integer amount;

    @Column(columnDefinition = "int(11) default 0 comment '运费(单位为分)'")
    private Integer freight;

    @Column(columnDefinition = "int(11) default 0 comment '订单总额(商品总额加运费)'")
    private Integer total;

    @Enumerated
    @Column(columnDefinition = "smallint(2) default 3 comment '订单状态'")
    private OrderStatus status;

    @Column(columnDefinition = "int(11) default 0 comment '优惠金额'")
    private Integer coupon;

    @Column(columnDefinition = "varchar(64) default '' comment '收货人地址'")
    private String address;

    @Column(columnDefinition = "varchar(24) default '' comment '收货人姓名'")
    private String username;

    @Column(columnDefinition = "char(13) default '' comment '收货人联系方式'")
    private String phone;

    @Column(columnDefinition = "varchar(255) default '' comment '备注'")
    private String remark;

    @Column(columnDefinition = "varchar(64) default '' comment '物流公司'")
    private String logisticsCompany;

    @Column(columnDefinition = "varchar(64) default '' comment '快递单号'")
    private String expressNumber;

    @Column(columnDefinition = "timestamp null comment '支付时间'")
    private Date payTime;

    @Column(columnDefinition = "timestamp null comment '发货时间'")
    private Date deliverTime;

    @Column(columnDefinition = "timestamp null comment '收货时间'")
    private Date receiveTime;

    @Column(columnDefinition = "timestamp null comment '完成时间'")
    private Date finishTime;


}
