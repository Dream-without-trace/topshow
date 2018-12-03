package com.luwei.services.user.web;

import com.luwei.common.enums.type.AgeType;
import com.luwei.common.enums.type.SexType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-14
 **/
@Data
@ApiModel
public class UserInfoAddDTO {

    @NotNull
    @ApiModelProperty("userId")
    private Integer userId;

    @ApiModelProperty("姓名")
    private String username;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("微博")
    private String microBlog;

    @ApiModelProperty("性别")
    private SexType sex;

    @ApiModelProperty("年龄段")
    private AgeType age;

}
