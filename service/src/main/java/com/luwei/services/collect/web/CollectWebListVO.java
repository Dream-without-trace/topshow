package com.luwei.services.collect.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-14
 **/
@Data
@ApiModel
public class CollectWebListVO {

    @ApiModelProperty("收藏id")
    private Integer collectId;

    @ApiModelProperty("商品或活动id")
    private Integer id;

    @ApiModelProperty("活动标题")
    private String title;

    @ApiModelProperty("图片")
    private String picture;

    @ApiModelProperty("价格")
    private Integer price;

    @ApiModelProperty("创建时间")
    private Date startTime;

    @ApiModelProperty("地址")
    private String address;
}
