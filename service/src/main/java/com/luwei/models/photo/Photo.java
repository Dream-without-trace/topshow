package com.luwei.models.photo;

import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * <p>用户相册</p>
 *
 * @author Leone
 * @since 2018-08-02
 **/
@Data
@Entity
public class Photo extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer photoId;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户id'")
    private Integer userId;

    @Column(columnDefinition = "text comment '图片'")
    private String picture;

    @Column(columnDefinition = "varchar(255) not null default '' comment '备注'")
    private String remark;
}
