package com.luwei.services.membershipCard.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @program: topshow
 * @description: 会员卡详情
 * @author: ZhangHongJie
 * @create: 2018-12-03 16:25
 **/
@Data
@ApiModel
public class MembershipCardDetailVo {


    @ApiModelProperty("Id")
    private Integer membershipCardId;

    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("封面")
    private String picture;

    @ApiModelProperty("描述")
    private String detail;

    @ApiModelProperty("会员权益")
    private String memberBenefits;

}
