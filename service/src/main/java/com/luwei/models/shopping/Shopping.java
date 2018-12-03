package com.luwei.models.shopping;

import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-28
 **/
@Data
@Entity
public class Shopping extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shoppingId;

    @Column(columnDefinition = "int(11) not null default 0 comment '购物车id'")
    private Integer userId;

    @Column(columnDefinition = "int(11) not null default 0 comment '商品id'")
    private Integer goodsId;

    @Column(columnDefinition = "int(11) not null default 0 comment '商家id'")
    private Integer storeId;

    @Column(columnDefinition = "varchar(128) not null default '' comment '商品名称'")
    private String goodsName;

    @Column(columnDefinition = "varchar(128) not null default '' comment '商品名称'")
    private String specificationName;

    @Column(columnDefinition = "int(11) not null default 0 comment '商家id'")
    private Integer specificationId;

    @Column(columnDefinition = "int(11) not null default 0 comment '商家id'")
    private Integer price;

    @Column(columnDefinition = "varchar(128) not null default '' comment '商品名称'")
    private String picture;

    @Column(columnDefinition = "int(11) not null default 0 comment '商家id'")
    private Integer count;


}
