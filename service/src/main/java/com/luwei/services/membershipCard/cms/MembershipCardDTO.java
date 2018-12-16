package com.luwei.services.membershipCard.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: topshow
 * @description: 会员卡
 * @author: ZhangHongJie
 * @create: 2018-12-12 14:30
 **/
@Data
@ApiModel
public class MembershipCardDTO {


    @ApiModelProperty("会员卡ID")
    private Integer membershipCardId;
    @NotNull(message = "有效时间不能为空")
    @ApiModelProperty("有效时间单位是天")
    private Integer effective;
    @NotNull(message = "地区Id不能为空")
    @ApiModelProperty("地区Id")
    private Integer areaId;
    @NotNull(message = "会员卡名称不能为空")
    @ApiModelProperty("会员卡名称")
    private String title;
    @NotNull(message = "会员卡封面不能为空")
    @ApiModelProperty("会员卡封面")
    private String picture;
    @NotNull(message = "会员卡描述不能为空")
    @ApiModelProperty("会员卡描述")
    private String detail;
    @NotNull(message = "会员卡权益不能为空")
    @ApiModelProperty("会员卡权益")
    private String memberBenefits;
    @NotNull(message = "会员卡价格不能为空")
    @ApiModelProperty("会员卡价格（单位是分）")
    private Integer price;

    @ApiModelProperty("地区名称")
    private String areaName;

}
