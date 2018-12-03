package com.luwei.services.area.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-17
 **/
@Data
public class CityVO implements Serializable {

    @ApiModelProperty("地区id")
    private Integer areaId;

    @ApiModelProperty("城市名")
    private String name;

    @ApiModelProperty("地区代码")
    private String code;

    @ApiModelProperty("区列表")
    List<DistrictVO> districtVOList;

}
