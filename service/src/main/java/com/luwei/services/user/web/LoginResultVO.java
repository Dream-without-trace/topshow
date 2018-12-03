package com.luwei.services.user.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jdq
 * @date 2017/12/15 16:22
 */
@Data
@AllArgsConstructor
public class LoginResultVO {

    @ApiModelProperty("userId")
    private Integer userId;

    @ApiModelProperty("token")
    private String token;

}
