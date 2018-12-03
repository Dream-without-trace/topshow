package com.luwei.services.specification.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Data
@ApiModel
public class SpecificationDetailVO implements Serializable {

    @ApiModelProperty("规格id")
    private Integer specificationId;

    @ApiModelProperty("规格名称")
    private String name;

    @ApiModelProperty("规格图片")
    private String picture;

    @ApiModelProperty("价格")
    private Integer price;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("库存")
    private Integer inventory;

}
