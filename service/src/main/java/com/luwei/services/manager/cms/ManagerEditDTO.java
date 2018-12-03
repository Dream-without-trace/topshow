package com.luwei.services.manager.cms;

import com.luwei.common.enums.type.RoleType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class ManagerEditDTO {

    @ApiModelProperty("管理员id")
    @NotNull(message = "id不能为空")
    private Integer managerId;

    @ApiModelProperty("管理员名称")
    private String username;

    @ApiModelProperty("管理员密码")
    private String password;

    @ApiModelProperty("管理员密码")
    private String account;

    @ApiModelProperty("管理员角色")
    private RoleType roleType;

}
