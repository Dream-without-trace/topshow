package com.luwei.services.activity.web;

import com.luwei.services.coupon.web.CouponOrderListVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-24
 **/
@Data
@ApiModel
public class ActivityAffirmVO {

    @ApiModelProperty("活动id")
    private Integer activityId;

    @ApiModelProperty("活动订单id")
    private Integer activityOrderId;

    @ApiModelProperty("活动标题")
    private String title;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("活动图片")
    private String picture;

    @ApiModelProperty("价格")
    private Integer price;

    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("数量")
    private Integer count;

    @ApiModelProperty("优惠券列表")
    private List<CouponOrderListVO> couponList;

}
