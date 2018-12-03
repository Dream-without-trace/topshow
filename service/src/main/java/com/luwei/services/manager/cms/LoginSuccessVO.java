package com.luwei.services.manager.cms;

import com.luwei.common.enums.type.RoleType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-08
 **/
@Data
public class LoginSuccessVO {

    @ApiModelProperty("角色")
    private RoleType role;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("名称")
    private String username;

    @ApiModelProperty("商家id")
    private Integer storeId;

    public LoginSuccessVO() {
    }

    public LoginSuccessVO(RoleType role, String token, String username) {
        this.role = role;
        this.token = token;
        this.username = username;
    }

    public LoginSuccessVO(RoleType role, String token, String username, Integer storeId) {
        this.role = role;
        this.token = token;
        this.username = username;
        this.storeId = storeId;
    }
}
