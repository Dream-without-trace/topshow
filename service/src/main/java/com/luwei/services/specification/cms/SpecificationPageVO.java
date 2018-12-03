package com.luwei.services.specification.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Data
@ApiModel
public class SpecificationPageVO implements Serializable {

    @ApiModelProperty("规格id")
    private Integer specificationId;

    @ApiModelProperty("规格名称")
    private String name;

    @ApiModelProperty("价格")
    private Integer price;

    @ApiModelProperty("规格图片")
    private String picture;

    @ApiModelProperty("库存")
    private Integer inventory;

    @ApiModelProperty("备注")
    private String remark;

}
