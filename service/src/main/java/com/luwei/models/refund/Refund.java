package com.luwei.models.refund;

import com.luwei.common.enums.status.RefundStatus;
import com.luwei.common.utils.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-20
 **/
@Data
@Entity
public class Refund extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer refundId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '商品规格名称'")
    private String goodsName;

    @Column(columnDefinition = "int(11) not null default 0 comment '商品数量'")
    private Integer goodsCount;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户id'")
    private Integer userId;

    @Column(columnDefinition = "int(11) not null default 0 comment '订单id'")
    private Integer orderId;

    @Column(columnDefinition = "int(11) not null default 0 comment '商品id'")
    private Integer goodsId;

    @Column(columnDefinition = "int(11) not null default 0 comment '规格id'")
    private Integer specificationId;

    @Column(columnDefinition = "int(11) not null default 0 comment '退款总额'")
    private Integer total;

    @Column(columnDefinition = "int(11) not null default 0 comment '店铺id'")
    private Integer storeId;

    @Column(columnDefinition = "varchar(64) default '' comment '外部订单号'")
    private String orderOutNo;

    @Column(columnDefinition = "varchar(24) not null default '' comment '收货人'")
    private String consignee;

    @Column(columnDefinition = "varchar(13) not null default '' comment '电话号码'")
    private String phone;

    @Column(columnDefinition = "varchar(24) not null default '' comment '收货人地址'")
    private String address;

    @Column(columnDefinition = "int(11) not null default 0 comment '退款状态'")
    private RefundStatus status;

    @Column(columnDefinition = "varchar(64) default '' comment '原因'")
    private String cause;

    @Column(columnDefinition = "varchar(255) default '' comment '选填原因'")
    private String optionCause;


}
