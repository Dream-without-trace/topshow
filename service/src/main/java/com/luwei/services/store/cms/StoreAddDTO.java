package com.luwei.services.store.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * <p> 添加
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Data
@ApiModel
public class StoreAddDTO {

    @NotNull
    @ApiModelProperty("店铺名称")
    private String name;

    @NotNull
    @ApiModelProperty("店铺地址")
    private String address;

    @NotNull
    @ApiModelProperty("店铺介绍")
    private String description;

    @NotNull
    @ApiModelProperty("商家封面")
    private String picture;

    @NotNull
    @ApiModelProperty("logo")
    private String logo;

    @NotNull
    @ApiModelProperty("商家电话")
    private String phone;

    @Email
    @NotNull
    @ApiModelProperty("商家邮箱")
    private String email;

}
