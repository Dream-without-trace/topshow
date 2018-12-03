package com.luwei.models.banner;

import com.luwei.common.enums.status.BannerStatus;
import com.luwei.common.enums.type.BannerType;
import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Leone
 * @since 2018-08-02
 **/
@Data
@Entity
public class Banner extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bannerId;

    @Column(columnDefinition = "varchar(255) not null default '' comment 'banner图片'")
    private String picture;

    @Column(columnDefinition = "varchar(255) not null default '' comment 'banner标题'")
    private String title;

    @Column(columnDefinition = "varchar(128) not null default '' comment '绑定的活动或商品名称'")
    private String name;

    @Column(columnDefinition = "varchar(255) not null default '' comment '跳转url'")
    private String url;

    @Column(columnDefinition = "varchar(255) not null default '' comment '说明'")
    private String remark;

    @Column(columnDefinition = "mediumint(11) not null default 0 comment '跳转的商品或活动的id'")
    private Integer id;

    @Enumerated
    @Column(columnDefinition = "tinyint not null default 0 comment 'banner类型'")
    private BannerType type;

    @Enumerated
    @Column(columnDefinition = "tinyint not null default 0 comment 'banner状态'")
    private BannerStatus status;

    @Column(columnDefinition = "smallint not null default 0 comment 'banner权重'")
    private Integer weight;

}
