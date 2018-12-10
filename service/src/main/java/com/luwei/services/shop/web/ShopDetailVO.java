package com.luwei.services.shop.web;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.luwei.services.membershipCard.web.MembershipCardDetailVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: topshow
 * @description: 门店详情
 * @author: ZhangHongJie
 * @create: 2018-12-03 15:56
 **/
@Data
@ApiModel
public class ShopDetailVO {

    @ApiModelProperty("门店Id")
    private Integer shopId;

    @ApiModelProperty("会员卡订单Id")
    private Integer membershipOrderCardId;

    @ApiModelProperty("门店名称")
    private String title;

    @ApiModelProperty("封面")
    private String picture;

    @ApiModelProperty("描述")
    private String detail;

    @ApiModelProperty("精彩图片")
    private List<String> previous;

    @ApiModelProperty("会员卡列表")
    private List<MembershipCardDetailVo> membershipCards;

    @ApiModelProperty("课程列表")
    private List<JSONObject> courses;






}
