package com.luwei.services.checkIn.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: topshow
 * @description: 签到返回
 * @author: ZhangHongJie
 * @create: 2018-12-04 14:42
 **/
@Data
@ApiModel
public class CheckInVo {

    @ApiModelProperty("连续签到天数")
    private Integer checkInDate;

    @ApiModelProperty("签到总积分")
    private Integer checkInTotalIntegral;

    @ApiModelProperty("签到日期")
    private List<Date> checkInTimes;

    @ApiModelProperty("是否签到 ：1：已签到，2：未签到")
    private Integer isCheckIn;


    public CheckInVo() {
    }

    public CheckInVo(Integer checkInDate, Integer checkInTotalIntegral, List<Date> checkInTimes, Integer isCheckIn) {
        this.checkInDate = checkInDate;
        this.checkInTotalIntegral = checkInTotalIntegral;
        this.checkInTimes = checkInTimes;
        this.isCheckIn = isCheckIn;
    }
}
