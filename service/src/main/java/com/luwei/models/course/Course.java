package com.luwei.models.course;

import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: topshow
 * @description: 课程
 * @author: ZhangHongJie
 * @create: 2018-12-03 16:06
 **/
@Data
@Entity
public class Course extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    @Column(columnDefinition = "int(11) not null default -1 comment '门店id'")
    private Integer shopId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '标题'")
    private String title;

    @Column(columnDefinition = "int(11) not null default 0 comment '课程日期（时间戳）'")
    private Integer startDate;

    @Column(columnDefinition = "varchar(64) not null default '' comment '开始时间'")
    private String startTime;

    @Column(columnDefinition = "varchar(64) not null default '' comment '结束时间'")
    private String endTime;

    @Column(columnDefinition = "int(11) not null default -1 comment '最大报名人数'")
    private Integer maxNum;

    @Column(columnDefinition = "text comment '封面'")
    private String picture;

    @Column(columnDefinition = "text comment '描述'")
    private String description;

}
