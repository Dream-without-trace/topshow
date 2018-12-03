package com.luwei.services.user.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Leone
 * @since 2018-08-13
 **/
@Data
public class UserQueryVO {

    @ApiModelProperty("昵称")
    private String nickname;

}
