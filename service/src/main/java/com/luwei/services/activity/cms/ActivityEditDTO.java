package com.luwei.services.activity.cms;

import com.luwei.common.enums.status.ActivityStatus;
import com.luwei.common.enums.type.ActivityType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.enums.type.TicketType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author Leone
 * @since 2018-08-10
 **/
@Data
@ApiModel
public class ActivityEditDTO {

    @NotNull(message = "活动id不能为空")
    @ApiModelProperty("活动id")
    private Integer activityId;

    @ApiModelProperty("活动标题")
    private String title;

    @ApiModelProperty("举办时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("举办地点")
    private String address;

    @ApiModelProperty("活动系列id")
    private Integer activitySeriesId;

    @ApiModelProperty("活动分类id")
    private Integer activityCategoryId;

    @ApiModelProperty("活动价格")
    private Integer price;

    @ApiModelProperty("购票须知")
    private String notice;

    @ApiModelProperty("往期风采")
    private List<String> previous;

    @ApiModelProperty("强力推荐")
    private FlagType recommend;

    @ApiModelProperty("活动详情")
    private String detail;

    @ApiModelProperty("活动封面")
    private String picture;

    @ApiModelProperty("票卷类型")
    private TicketType ticketType;

    @ApiModelProperty("地区id")
    private Integer areaId;

    @ApiModelProperty("退款说明")
    private String refundExplain;

    @ApiModelProperty("活动类型")
    private ActivityType activityType;
}
