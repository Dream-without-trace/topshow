package com.luwei.services.store.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p> 添加
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Data
@ApiModel
public class StoreEditDTO {

    @NotNull
    @ApiModelProperty("商家名称")
    private Integer storeId;

    @ApiModelProperty("店铺名称")
    private String name;

    @ApiModelProperty("店铺地址")
    private String address;

    @ApiModelProperty("店铺介绍")
    private String description;

    @ApiModelProperty("店铺电话")
    private String phone;

    @ApiModelProperty("店铺邮箱")
    private String email;

    @ApiModelProperty("商家封面")
    private String picture;

    @ApiModelProperty("logo")
    private String logo;

}
