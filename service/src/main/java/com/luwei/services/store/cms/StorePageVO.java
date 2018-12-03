package com.luwei.services.store.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p> 分页vo
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Data
@ApiModel
public class StorePageVO implements Serializable {

    @ApiModelProperty("店铺id")
    private Integer storeId;

    @ApiModelProperty("店铺名称")
    private String name;

    @ApiModelProperty("店铺地址")
    private String address;

    @ApiModelProperty("店铺电话")
    private String phone;

    @ApiModelProperty("店铺邮箱")
    private String email;

    @ApiModelProperty("店铺介绍")
    private String description;

    @ApiModelProperty("入驻时间")
    private Date createTime;

    @ApiModelProperty("商家封面")
    private String picture;

    @ApiModelProperty("logo")
    private String logo;

    @ApiModelProperty("客服id")
    private String customerId;

}
