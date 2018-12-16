package com.luwei.services.user.cms;

import com.luwei.common.enums.type.FlagType;
import com.luwei.services.activity.cms.ActivityCmsVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Leone
 * @since 2018-08-13
 **/
@Data
public class UserPageVO implements Serializable {

    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("所在地区")
    private String area;
    @ApiModelProperty("当前积分")
    private Integer integral;
    @ApiModelProperty("用户活动")
    private List<ActivityCmsVO> activity;
    @ApiModelProperty("是否禁用")
    private FlagType disable;
    @ApiModelProperty("用户地址")
    private String address;
    @ApiModelProperty("今日是否签到  1未签到  2已签到")
    private Integer isCheckIn;
    @ApiModelProperty("是否完成任务  1未完成  2已完成")
    private Integer isTaskFinish;
    @ApiModelProperty("会员等级名称")
    private String membershipCardName;

}
