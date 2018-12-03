package com.luwei.models.manager;

import com.luwei.common.enums.type.ManagerType;
import com.luwei.common.enums.type.RoleType;
import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Leone
 * @since 2018-07-30
 **/
@Data
@Entity
@Table(indexes = {@Index(name = "account_inx", columnList = "account")})
public class Manager extends IdEntity {

    private static final long serialVersionUID = -9206021799138212482L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer managerId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '管理员账号'")
    private String account;

    @Column(columnDefinition = "int(11) not null default 0 comment '商家管理员'")
    private Integer storeId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '管理员密码'")
    private String password;

    @Column(columnDefinition = "varchar(64) not null default '' comment '管理员用户名'")
    private String username;

    @Column(columnDefinition = "char(13) not null default '' comment '联系方式'")
    private String phone;

    @Enumerated
    @Column(columnDefinition = "tinyint not null default 1 comment '0超级管理员,1平台管理员,2商家管理员'")
    private RoleType role;

    @Column(columnDefinition = "tinyint not null default 0 comment '是否被禁用'")
    private Boolean disabled;

    @Enumerated
    @Column(columnDefinition = "tinyint not null default 0 comment '管理员类型'")
    private ManagerType managerType;

}
