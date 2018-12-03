package com.luwei.services.goods.cms;

import com.luwei.common.enums.status.SalesStatus;
import com.luwei.common.enums.type.GoodsType;
import com.luwei.services.specification.cms.SpecificationDetailVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Leone
 * @since 2018-08-08
 **/
@Data
@ApiModel
public class GoodsPageVO implements Serializable {

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("商家信息")
    private Integer storeId;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品类别名称")
    private String categoryName;

    @ApiModelProperty("商品类别id")
    private Integer categoryId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("商品图片")
    private String picture;

    @ApiModelProperty("商品价格")
    private Integer price;

    @ApiModelProperty("详情")
    private String detail;

    @ApiModelProperty("商品参数")
    private String parameters;

    @ApiModelProperty("商品运费")
    private Integer freight;

    @ApiModelProperty("是否上架")
    private SalesStatus status;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty("商品类型")
    private GoodsType goodsType;

    @ApiModelProperty("商品规格列表")
    private List<SpecificationDetailVO> specificationList;

}
