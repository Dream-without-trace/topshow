package com.luwei.services.manager.cms;

import com.luwei.common.enums.type.RoleType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Data
public class ManagerPageVO {

    @ApiModelProperty("主键id")
    private Integer managerId;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("名称")
    private String username;

    @ApiModelProperty("管理员角色")
    private RoleType role;

    @ApiModelProperty("是否已被禁用。false:未禁用(默认)，true:已禁用")
    private Boolean disabled;

}
