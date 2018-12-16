package com.luwei.services.membershipCard.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: topshow
 * @description: 会员卡订单添加类
 * @author: ZhangHongJie
 * @create: 2018-12-03 19:51
 **/
@Data
@ApiModel
public class MembershipCardCMSAddOrder {

    @NotNull
    @ApiModelProperty("会员卡id")
    private Integer membershipCardId;

    @NotNull
    @ApiModelProperty("用户id")
    private Integer userId;


}
