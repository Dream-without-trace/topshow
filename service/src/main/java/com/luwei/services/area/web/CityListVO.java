package com.luwei.services.area.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-22
 **/
@Data
@ApiModel
public class CityListVO {

    @ApiModelProperty("地区id")
    private Integer areaId;

    @ApiModelProperty("城市名")
    private String name;

    @ApiModelProperty("拼音")
    private String spell;

    @ApiModelProperty("城市图片")
    private String picture;

}
