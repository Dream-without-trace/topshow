package com.luwei.models.order.detail;

import com.luwei.common.enums.type.FlagType;
import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * <p>订单详情
 *
 * @author Leone
 * @since 2018-08-02
 **/
@Data
@Entity
public class OrderDetail extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailId;

    @Column(columnDefinition = "int(11) not null default 0 comment '商品id'")
    private Integer goodsId;

    @Column(columnDefinition = "int(11) not null default 0 comment '订单id'")
    private Integer orderId;

    @Column(columnDefinition = "varchar(255) not null default '' comment '商品名称'")
    private String name;

    @Column(columnDefinition = "int(11) not null default 0 comment '商品价格'")
    private Integer price;

    @Column(columnDefinition = "smallint(11) not null default 0 comment '数量'")
    private Integer count;

    @Column(columnDefinition = "varchar(255) not null default '' comment '商品图片'")
    private String picture;

    @Column(columnDefinition = "int(11) not null default 0 comment '规格id'")
    private Integer specificationId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '规格名称'")
    private String specificationName;

    @Column(columnDefinition = "tinyint(2) not null default 0 comment '是否评价'")
    private FlagType flagType;

}
