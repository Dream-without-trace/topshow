package com.luwei.models.address;

import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Leone
 * @since 2018-08-01
 **/
@Data
@Entity
public class Address extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户ID'")
    private String userId;

    @Column(columnDefinition = "varchar(24) not null default '' comment '省'")
    private String province;

    @Column(columnDefinition = "varchar(24) not null default '' comment '市'")
    private String city;

    @Column(columnDefinition = "varchar(24) not null default '' comment '区/县'")
    private String region;

    @Column(columnDefinition = "varchar(255) not null default '' comment '街道'")
    private String street;

    @Column(columnDefinition = "varchar(255) not null default '' comment '详细地址信息'")
    private String address;

    @Column(columnDefinition = "varchar(24) default '' comment '邮编'")
    private String postCode;

    @Column(columnDefinition = "varchar(64) not null default '' comment '收货人'")
    private String username;

    @Column(columnDefinition = "char(13) not null default '' comment '联系方式'")
    private String phone;

    @Column(columnDefinition = "tinyint not null default 0 comment '是否是默认'")
    private Boolean acquiescence;

}
