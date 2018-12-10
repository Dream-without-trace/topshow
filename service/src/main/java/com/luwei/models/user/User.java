package com.luwei.models.user;

import com.luwei.common.enums.type.AgeType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.enums.type.SexType;
import com.luwei.common.utils.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Leone
 * @since 2018-07-30
 **/
@Data
@Entity
@Table(indexes = {@Index(name = "phone_inx", columnList = "phone"), @Index(name = "open_inx", columnList = "openid")})
public class User extends IdEntity {

    private static final long serialVersionUID = 2786361327820044278L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(columnDefinition = "int(11) default 0 comment '推荐用户Id'")
    private Integer recommenderUserId;

    @Column(columnDefinition = "varchar(48) not null default '' comment '姓名'")
    private String username;

    @Column(columnDefinition = "varchar(48) not null default '' comment '昵称'")
    private String nickname;

    @Column(columnDefinition = "varchar(64) not null comment '微信openid'")
    private String openid;

    @Column(columnDefinition = "varchar(64) not null comment '微信头像URL'")
    private String avatarUrl;

    @Column(columnDefinition = "char(13) default '' comment '手机号'")
    private String phone;

    @Column(columnDefinition = "int(11) default 0 comment '用户积分'")
    private Integer integral;

    @Column(columnDefinition = "tinyint(1) default 0 comment '用户性别'")
    private SexType sex;

    @Column(columnDefinition = "varchar(64) default '' comment '微博'")
    private String microBlog;

    @Column(columnDefinition = "tinyint(1) default 0 comment '年龄'")
    private AgeType age;

    @Column(columnDefinition = "varchar(48) not null default '' comment '城市'")
    private String city;

    @Column(columnDefinition = "varchar(48) not null default '' comment '省'")
    private String province;

    @Column(columnDefinition = "tinyint not null default 0 comment '是否禁用'")
    private FlagType disable;

    @Column(columnDefinition = "bit not null default 1 comment 'true完善过个人信息false未完善过个人信息'")
    private Boolean first;

    @Column(columnDefinition = "varchar(48) default '' comment '宝宝小名'")
    private String babyName;

    @Column(columnDefinition = "tinyint(1) default 0 comment '宝宝性别'")
    private SexType babySex;

}
