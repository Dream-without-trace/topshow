package com.luwei.services.user.cms;

import com.luwei.common.enums.type.AgeType;
import com.luwei.common.enums.type.SexType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * @program: topshow
 * @description: 用户详情
 * @author: ZhangHongJie
 * @create: 2018-12-06 11:52
 **/
@Data
public class UserInfoVo {

    @ApiModelProperty("主键")
    private Integer userId;

    @ApiModelProperty("姓名")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("微信头像URL")
    private String avatarUrl;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("用户积分")
    private Integer integral;

    @ApiModelProperty("用户性别")
    private SexType sex;

    @ApiModelProperty("用户年龄")
    private AgeType age;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("今日是否已签到 ：1：已签到，2：未签到")
    private Integer isCheckIn;

    @ApiModelProperty("连续签到天数")
    private Integer checkInDate;

    @ApiModelProperty("签到总积分")
    private Integer checkInTotalIntegral;

    @ApiModelProperty("签到日期")
    private List<String> checkInTimes;


    public UserInfoVo() {
    }

    public UserInfoVo(Integer userId, String username, String nickname, String avatarUrl, String phone, Integer integral,
                      SexType sex, AgeType age, String city, String province, Integer isCheckIn, Integer checkInDate,
                      Integer checkInTotalIntegral, List<String> checkInTimes) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.phone = phone;
        this.integral = integral;
        this.sex = sex;
        this.age = age;
        this.city = city;
        this.province = province;
        this.isCheckIn = isCheckIn;
        this.checkInDate = checkInDate;
        this.checkInTotalIntegral = checkInTotalIntegral;
        this.checkInTimes = checkInTimes;
    }
}
