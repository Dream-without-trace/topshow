package com.luwei.services.manager.cms;

import com.luwei.common.enums.type.RoleType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-14
 **/
@Data
@ApiModel
public class ManagerAddDTO {

    @ApiModelProperty("账户")
    @NotNull(message = "请输入管理员账户")
    @Length(max = 18, min = 6, message = "账号长度6-18")
    private String account;

    @ApiModelProperty("密码")
    @NotNull(message = "请输入管理员密码")
    private String password;

    @ApiModelProperty("名称")
    @NotNull(message = "请输入管理员名称")
    private String username;

    @ApiModelProperty("角色")
    @NotNull(message = "请输入管理员角色")
    private RoleType roleType;

    @ApiModelProperty("商家id")
    private Integer storeId;

}
