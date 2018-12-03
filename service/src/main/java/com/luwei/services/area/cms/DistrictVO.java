package com.luwei.services.area.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-17
 **/
@Data
public class DistrictVO implements Serializable {

    @ApiModelProperty("地区id")
    private Integer areaId;

    @ApiModelProperty("地区名称")
    private String name;

    @ApiModelProperty("地区代码")
    private String code;

}
