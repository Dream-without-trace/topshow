package com.luwei.models.activity.category;

import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Leone
 * @since 2018-08-09
 **/
@Data
@Entity
public class ActivityCategory extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityCategoryId;

    @Column(columnDefinition = "varchar(64) not null default 0 comment '分类标题'")
    private String title;

    @Column(columnDefinition = "tinyint(11) default 0 comment '系列'")
    private Integer series;

    @Column(columnDefinition = "varchar(255) not null default '' comment '备注'")
    private String remark;

}
