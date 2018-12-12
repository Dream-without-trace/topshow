package com.luwei.services.activity.cms;

import com.luwei.common.enums.type.ActivityType;
import com.luwei.common.enums.type.FlagType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Data
@ApiModel
public class ActivityAddDTO {

    @NotNull(message = "活动标题不能为空")
    @ApiModelProperty("活动标题")
    private String title;

    @NotNull(message = "举办时间不能为空")
    @ApiModelProperty("举办时间")
    private Date startTime;

    @NotNull(message = "结束时间不能为空")
    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("举办地点")
    private String address;

    @ApiModelProperty("活动分类")
    private Integer activityCategoryId;

    @ApiModelProperty("活动系列")
    private Integer activitySeriesId;

    @ApiModelProperty("活动价格")
    private Integer price;

    @ApiModelProperty("购票须知")
    private String notice;

    @ApiModelProperty("往期风采")
    private List<String> previous;

    @NotNull(message = "强力推荐不能为空")
    @ApiModelProperty("强力推荐")
    private FlagType recommend;

    @NotNull(message = "活动详情不能为空")
    @ApiModelProperty("活动详情")
    private String detail;

    @NotNull(message = "活动封面不能为空")
    @ApiModelProperty("活动封面")
    private String picture;

    @NotNull(message = "地区id不能为空")
    @ApiModelProperty("地区id")
    private Integer areaId;

    @ApiModelProperty("退款说明")
    private String refundExplain;

    @NotNull(message = "活动类型不能为空")
    @ApiModelProperty("活动类型")
    private ActivityType activityType;




}
