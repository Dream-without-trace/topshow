package com.luwei.models.collect;

import com.luwei.common.enums.type.CollectType;
import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Leone
 * @since 2018-08-02
 **/
@Data
@Entity
public class Collect extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer collectId;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户id'")
    private Integer userId;

    @Column(columnDefinition = "int(11) not null default 0 comment '收藏的活动或商品id'")
    private Integer id;

    @Enumerated
    @Column(columnDefinition = "tinyint(11) not null default 0 comment '收藏的类型(商品或活动)'")
    private CollectType type;

}
