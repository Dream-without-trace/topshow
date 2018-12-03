package com.luwei.services.user.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Leone
 * @since 2018-08-13
 **/
@Data
public class UserEditDTO implements Serializable {

    @ApiModelProperty("主键")
    private Integer userId;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别，0:未知，1：男，2：女")
    private Integer gender;


}
