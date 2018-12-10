package com.luwei.models.courseEnrolment;

import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: topshow
 * @description: 报名课程
 * @author: ZhangHongJie
 * @create: 2018-12-04 23:33
 **/
@Data
@Entity
public class CourseEnrolment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseEnrolmentId;

    @Column(columnDefinition = "int(11) not null default -1 comment '门店id'")
    private Integer shopId;

    @Column(columnDefinition = "int(11) not null default -1 comment '课程id'")
    private Integer courseId;

    @Column(columnDefinition = "int(11) not null default -1 comment '用户id'")
    private Integer userId;

    @Column(columnDefinition = "timestamp not null default current_timestamp comment '报名时间'")
    private Date createTime;

    @Column(columnDefinition = "smallint(1) not null default 1 comment '是否已验票 ：1，未验票，2，已验票'")
    private Integer isInspectTicket;


    public CourseEnrolment() {
    }

    public CourseEnrolment(Integer shopId, Integer courseId, Integer userId, Date createTime, Integer isInspectTicket) {
        this.shopId = shopId;
        this.courseId = courseId;
        this.userId = userId;
        this.createTime = createTime;
        this.isInspectTicket = isInspectTicket;
    }
}
