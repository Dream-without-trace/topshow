package com.luwei.models.checkIn;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: topshow
 * @description: 每日签到
 * @author: ZhangHongJie
 * @create: 2018-12-04 14:18
 **/
@Data
@Entity
public class CheckIn{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer checkInId;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户id'")
    private Integer userId;

    @Column(columnDefinition = "int(11) not null default 0 comment '签到赠送积分'")
    private Integer giveIntegral;

    @Column(columnDefinition = "int(11) not null default 0 comment '连续签到天数'")
    private Integer checkInDate;

    @Column(columnDefinition = "timestamp not null default current_timestamp comment '签到时间'")
    private Date checkInTime;


    public CheckIn() {
    }

    public CheckIn(Integer userId, Integer giveIntegral, Integer checkInDate, Date checkInTime) {
        this.userId = userId;
        this.giveIntegral = giveIntegral;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
    }
}
