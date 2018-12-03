package com.luwei.services.activity.web;

import com.luwei.common.enums.type.FlagType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Data
@ApiModel
public class ActivityDetailVO {

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("价格")
    private Integer price;

    @ApiModelProperty("封面")
    private String picture;

    @ApiModelProperty("详情")
    private String detail;

    @ApiModelProperty("购票须知")
    private String notice;

    @ApiModelProperty("精彩图片")
    private List<String> previous;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("是否收藏")
    private FlagType collect;

    @ApiModelProperty("退款说明")
    private String refundExplain;

    @ApiModelProperty("活动")
    private String hello;


}
