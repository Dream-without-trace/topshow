package com.luwei.models.property;

import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * 属性
 *
 * @author Leone
 * @since 2018-08-08
 **/
@Data
@Entity
public class Property extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer propertyId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '属性名称'")
    private String name;

    @Column(columnDefinition = "int(11) default 0 comment '属性价格'")
    private Integer price;

    @Column(columnDefinition = "int(11) default 0 comment '规格id'")
    private Integer specificationId;

    @Column(columnDefinition = "varchar(255) not null default '' comment '属性图片'")
    private String picture;

    @Column(columnDefinition = "int(11) not null default 0 comment '库存'")
    private Integer inventory;

}
