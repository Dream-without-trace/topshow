package com.luwei.services.manager.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Data
@ApiModel
public class ManagerQueryDTO {

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("名称")
    private String username;

}
