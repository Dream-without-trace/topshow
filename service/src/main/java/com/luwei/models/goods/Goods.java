package com.luwei.models.goods;

import com.luwei.common.enums.status.SalesStatus;
import com.luwei.common.enums.type.GoodsType;
import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Data
@Entity
public class Goods extends IdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goodsId;

    @Column(columnDefinition = "int(11) not null default 0 comment '店铺id'")
    private Integer storeId;

    @Column(columnDefinition = "smallint(11) not null default 0 comment '运费'")
    private Integer freight;

    @Column(columnDefinition = "varchar(255) not null default '' comment '商品名称'")
    private String name;

    @Column(columnDefinition = "int(11) not null default 0 comment '商品类别id'")
    private Integer categoryId;

    @Column(columnDefinition = "text comment '商品图片'")
    private String picture;

    @Column(columnDefinition = "text comment '商品详情'")
    private String detail;

    @Column(columnDefinition = "text comment '商品参数'")
    private String parameters;

    @Column(columnDefinition = "int(11) not null default 0 comment '商品价格(默认规格的最小价格)'")
    private Integer price;

    @Column(columnDefinition = "smallint(11) default 1 comment '权重'")
    private Integer weight;

    @Enumerated
    @Column(columnDefinition = "tinyint(11) comment '销售状态上架和下架'")
    private SalesStatus status;

    @Column(columnDefinition = "int(11) not null default 0 comment '销量'")
    private Integer sales;

    @Enumerated
    @Column(columnDefinition = "tinyint(11) default 0 comment '商品类型'")
    private GoodsType goodsType;

}
