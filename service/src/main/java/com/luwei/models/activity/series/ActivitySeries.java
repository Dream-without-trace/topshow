package com.luwei.models.activity.series;

import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Leone
 * @since 2018-08-09
 **/
@Data
@Entity
public class ActivitySeries extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activitySeriesId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '分类标题'")
    private String title;

    @Column(columnDefinition = "smallint(11) default 0 comment '系列'")
    private Integer count;

    @Column(columnDefinition = "varchar(255) default '' comment '备注'")
    private String remark;

}
