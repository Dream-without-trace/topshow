package com.luwei.services.user.address.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-29
 **/
@Data
@ApiModel
public class UserReceivingAddDTO {

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("用户id")
    private Integer userId;

    @NotNull(message = "用户联系方式不能为空")
    @ApiModelProperty("用户联系方式")
    private String phone;

    @NotNull(message = "用户联系方式不能为空")
    @ApiModelProperty("用户联系方式")
    private String username;

    @NotNull(message = "完整收货地址不能为空")
    @ApiModelProperty("完整收货地址")
    private String fullAddress;

    @ApiModelProperty("邮政编码")
    private String postalCode;

    @NotNull
    @ApiModelProperty("地址数组")
    private List<String> address;

}
