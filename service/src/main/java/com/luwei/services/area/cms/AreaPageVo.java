package com.luwei.services.area.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @program: topshow
 * @description: 地区列表
 * @author: ZhangHongJie
 * @create: 2018-12-09 17:54
 **/
@Data
public class AreaPageVo {

    @ApiModelProperty("地区id")
    private Integer areaId;
    @ApiModelProperty("上级地区id")
    private Integer parentId;
    @ApiModelProperty("上级地区")
    private String parentName;
    @ApiModelProperty("拼音")
    private String pinyin;
    @ApiModelProperty("城市图片")
    private String picture;
    @ApiModelProperty("拼音首字母简写")
    private String initials;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("城市编码")
    private String code;
    @ApiModelProperty("排序")
    private Integer sort;

}
