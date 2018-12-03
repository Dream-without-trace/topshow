package com.luwei.services.manager.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author jdq
 * @date 2017/12/9 17:19
 */
@Data
public class ManagerLoginVO {

    @ApiModelProperty("账户")
    @NotBlank(message = "账户不能为空")
    private String account;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @ApiModelProperty("uuid")
    @NotBlank(message = "uuid不能为空")
    private String uuid;

}
