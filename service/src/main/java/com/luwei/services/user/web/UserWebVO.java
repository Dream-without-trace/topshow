package com.luwei.services.user.web;

import com.luwei.common.enums.type.FlagType;
import com.luwei.common.enums.type.SexType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Data
@ApiModel
public class UserWebVO {

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户ID")
    private String openid;

    @ApiModelProperty("用户头像")
    private String avatarUrl;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户性别")
    private SexType sex;

    @ApiModelProperty("用户积分")
    private Integer integral;

    @ApiModelProperty("是否绑定手机号")
    private FlagType isBingingPhone;

    @ApiModelProperty("是否完善个人信息")
    private FlagType isCompleteInfo;

    @ApiModelProperty("是否有收货地址")
    private FlagType isAddress;

}
