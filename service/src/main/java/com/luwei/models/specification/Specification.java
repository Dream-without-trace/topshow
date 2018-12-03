package com.luwei.models.specification;

import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * 规格
 *
 * @author Leone
 * @since 2018-08-02
 **/
@Data
@Entity
public class Specification extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer specificationId;

    @Column(columnDefinition = "int(11) not null default 0 comment '商品id'")
    private Integer goodsId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '规格名称'")
    private String name;

    @Column(columnDefinition = "varchar(255) not null default 0 comment '规格图片'")
    private String picture;

    @Column(columnDefinition = "int(11) not null default 0 comment '规格价格'")
    private Integer price;

    @Column(columnDefinition = "int(11) not null default 0 comment '规格库存'")
    private Integer inventory;

    @Column(columnDefinition = "varchar(255) not null default '' comment '备注'")
    private String remark;

}
