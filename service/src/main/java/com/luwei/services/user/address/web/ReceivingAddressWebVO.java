package com.luwei.services.user.address.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-29
 **/
@Data
@ApiModel
public class ReceivingAddressWebVO {

    @ApiModelProperty("用户收货地址id")
    private Integer receivingAddressId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty("用户联系方式")
    private String phone;

    @ApiModelProperty("完整收货地址")
    private String fullAddress;

}
