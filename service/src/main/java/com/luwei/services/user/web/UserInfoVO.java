package com.luwei.services.user.web;

import com.luwei.common.enums.type.AgeType;
import com.luwei.common.enums.type.SexType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-14
 **/
@Data
@ApiModel
public class UserInfoVO {

    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty("用户头像")
    private String phone;

    @ApiModelProperty("微博")
    private String microBlog;

    @ApiModelProperty("性别")
    private SexType sex;

    @ApiModelProperty("子女年龄段")
    private AgeType age;

}
