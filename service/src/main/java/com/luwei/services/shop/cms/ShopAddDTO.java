package com.luwei.services.shop.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: topshow
 * @description: 门店新增
 * @author: ZhangHongJie
 * @create: 2018-12-03 15:10
 **/
@Data
@ApiModel
public class ShopAddDTO {


    @NotNull(message = "门店名称不能为空")
    @ApiModelProperty("门店名称")
    private String title;

    @NotNull(message = "地区id不能为空")
    @ApiModelProperty("地区id")
    private Integer areaId;

    @NotNull(message = "门店封面不能为空")
    @ApiModelProperty("门店封面")
    private String picture;

    @NotNull(message = "门店详情不能为空")
    @ApiModelProperty("门店详情")
    private String detail;

    @ApiModelProperty("往期风采")
    private List<String> previous;


}
