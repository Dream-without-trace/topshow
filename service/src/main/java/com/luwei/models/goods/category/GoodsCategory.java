package com.luwei.models.goods.category;

import com.luwei.common.utils.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Leone
 * @since 2018-08-02
 **/
@Data
@Entity
public class GoodsCategory extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @ApiModelProperty(value = "父Id")
    private Integer parentId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '名称'")
    private String name;

    @Column(columnDefinition = "varchar(255) not null default '' comment '图片'")
    private String picture;

    @Column(columnDefinition = "varchar(255) not null default '' comment '说明'")
    private String description;


}
