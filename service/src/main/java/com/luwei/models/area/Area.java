package com.luwei.models.area;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-17
 **/
@Data
@Entity
public class Area implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer areaId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '名称'")
    private String name;

    @Column(columnDefinition = "int(255) not null default 0 comment '父id'")
    private Integer parentId;

    @Column(columnDefinition = "varchar(255) not null default '' comment '拼音首字母简写'")
    private String initials;

    @Column(columnDefinition = "varchar(255) not null default '' comment '拼音'")
    private String pinyin;

    @Column(columnDefinition = "varchar(255) not null default '' comment '行政级别'")
    private String suffix;

    @Column(columnDefinition = "smallint(11) not null default 0 comment '排序'")
    private Integer sort;

    @Column(columnDefinition = "char(24) not null default '' comment '代码'")
    private String code;

    @Column(columnDefinition = "varchar(128) not null default '' comment '城市图片'")
    private String picture;


}
