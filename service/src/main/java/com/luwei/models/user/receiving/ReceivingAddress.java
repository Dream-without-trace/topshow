package com.luwei.models.user.receiving;

import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-29
 **/
@Data
@Entity
public class ReceivingAddress extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer receivingAddressId;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户id'")
    private Integer userId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '收货人'")
    private String username;

    @Column(columnDefinition = "char(13) default '' comment '联系电话'")
    private String phone;

    @Column(columnDefinition = "varchar(24) default '' comment '省'")
    private String province;

    @Column(columnDefinition = "varchar(24) default '' comment '城市'")
    private String city;

    @Column(columnDefinition = "varchar(64) default '' comment '(区/县)'")
    private String region;

    @Column(columnDefinition = "varchar(255) default '' comment '详细地址'")
    private String address;

    @Column(columnDefinition = "varchar(12) default '' comment '邮政编码'")
    private String postalCode;

}
