package com.luwei.services.area.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: topshow
 * @description: 添加
 * @author: ZhangHongJie
 * @create: 2018-12-09 18:15
 **/
@Data
@ApiModel
public class AreaDTO {

    @ApiModelProperty("地区id")
    private Integer areaId;
    @NotNull(message = "名称不能为空")
    @ApiModelProperty("名称")
    private String name;
    @NotNull(message = "上级Id不能为空")
    @ApiModelProperty("上级id")
    private Integer parentId;
    @ApiModelProperty("拼音首字母简写")
    private String initials;
    @NotNull(message = "拼音不能为空")
    @ApiModelProperty("拼音")
    private String pinyin;
    @ApiModelProperty("行政级别")
    private String suffix;
    @NotNull(message = "排序不能为空")
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("代码")
    private String code;
    @ApiModelProperty("城市图片")
    private String picture;

}
