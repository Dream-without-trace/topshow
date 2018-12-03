package com.luwei.models.store;

import com.luwei.common.enums.status.StoreStatus;
import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * 商家信息
 *
 * @author Leone
 * @since 2018-08-01
 **/
@Data
@Entity
public class Store extends IdEntity {

    private static final long serialVersionUID = 8599583375952631881L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;

    @Column(columnDefinition = "varchar(64) not null comment '店铺名称'")
    private String name;

    @Column(columnDefinition = "varchar(255) default '' comment '店铺地址'")
    private String address;

    @Column(columnDefinition = "varchar(255) default '' comment '店铺介绍'")
    private String description;

    @Column(columnDefinition = "varchar(255) default '' comment '商家封面'")
    private String picture;

    @Column(columnDefinition = "varchar(255) default '' comment 'logo'")
    private String logo;

    @Column(columnDefinition = "varchar(36) default '' comment '邮箱'")
    private String email;

    @Column(columnDefinition = "char(13) default '' comment '电话号码'")
    private String phone;

    @Column(columnDefinition = "smallint(2) default 3 comment '状态。1上架，2下架, 3待上架'")
    private StoreStatus status;

    @Column(columnDefinition = "varchar(24) not null default '' comment '客服id'")
    private String customerId;

}

