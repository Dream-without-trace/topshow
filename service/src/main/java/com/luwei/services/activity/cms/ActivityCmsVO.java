package com.luwei.services.activity.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-24
 **/
@Data
@ApiModel
public class ActivityCmsVO implements Serializable {

    @ApiModelProperty("活动订单id")
    private Integer activityOrderId;

    @ApiModelProperty("活动id")
    private Integer activityId;

    @ApiModelProperty("活动标题")
    private String title;

    @ApiModelProperty("举办时间")
    private Date startTime;

    @ApiModelProperty("举办地点")
    private String address;

    public ActivityCmsVO() {
    }

    public ActivityCmsVO(Integer activityOrderId, Integer activityId, String title, Date startTime, String address) {
        this.activityOrderId = activityOrderId;
        this.activityId = activityId;
        this.title = title;
        this.startTime = startTime;
        this.address = address;
    }
}
