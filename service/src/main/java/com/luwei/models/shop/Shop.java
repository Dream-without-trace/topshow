package com.luwei.models.shop;

import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @program: topshow
 * @description: 门店
 * @author: ZhangHongJie
 * @create: 2018-12-03 12:33
 **/
@Data
@Entity
public class Shop extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '门店名称'")
    private String title;

    @Column(columnDefinition = "int(11) not null default -1 comment '地区id'")
    private Integer areaId;

    @Column(columnDefinition = "text comment '门店封面'")
    private String picture;

    @Column(columnDefinition = "text comment '详情描述'")
    private String detail;

    @Column(columnDefinition = "text comment '往期风采'")
    private String previous;



}
