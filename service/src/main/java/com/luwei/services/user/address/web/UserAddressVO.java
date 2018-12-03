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
 * @since 2018-09-01
 **/
@Data
@ApiModel
public class UserAddressVO {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户联系方式")
    private String phone;

    @ApiModelProperty("用户联系方式")
    private String username;

    @ApiModelProperty("完整收货地址")
    private String fullAddress;

    @ApiModelProperty("邮政编码")
    private String postalCode;

    @ApiModelProperty("地址数组")
    private List<String> address;

}
